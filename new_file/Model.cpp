#include "Debug.hpp"
#include "Model.hpp"
#include "define/Define.hpp"

#include <stack>

#include <cctype>
#include <cstdio>


namespace rpg2kLib
{
	namespace model
	{
		Base::Base(SystemString const& dir)
		: fileDir_(dir), fileName_( defaultName() )
		{
			checkExists();
		}
		Base::Base(SystemString const& dir, SystemString const& name)
		: fileDir_(dir), fileName_(name)
		{
			checkExists();
		}
		Base::~Base()
		{
		}

		void Base::reset()
		{
			getData().clear();
			structure::Map< uint, structure::Descriptor >& info = getDescriptor();
			for(structure::Map< uint, structure::Descriptor >::Iterator it = info.begin(); it != info.end(); ++it) {
				getData().add( it.first(), it.second() );
			}
		}

		structure::Element& Base::operator [](uint index)
		{
			return getData()[0].getArray1D()[index];
		}
		structure::Element const& Base::operator [](uint index) const
		{
			return getData()[0].getArray1D()[index];
		}

		structure::Map< uint, structure::Descriptor >& Base::getDescriptor() const
		{
			return DefineLoader::instance().get( getHeader() );
		}

		void Base::checkExists()
		{
			FILE* fp = std::fopen( (fileDir_ + PATH_SEPR + fileName_).c_str(), "rb");

			if(fp == NULL) exists_ = false;
			else {
				fclose(fp);
				exists_ = true;
			}
		}

		void Base::load()
		{
			if(fileName_ == "") fileName_ = defaultName();

			if( !exists() ) throw std::runtime_error("\"" + getFileName() + "\"" + " not found.");

			structure::StreamReader s( getFileName() );
			if( !s.checkHeader( getHeader() ) ) throw std::runtime_error("Stream Invalid header.");

			structure::Map< uint, structure::Descriptor >& info = getDescriptor();

			for(structure::Map< uint, structure::Descriptor >::Iterator it = info.begin(); it != info.end(); ++it) {
				getData().add( it.first(), it.second(), s );
			}

			if( s.eof() ) return;
			else {
				cerr << s.name() << " didn't end correctly. tell(): " << s.tell() << ";" << endl;

				cerr << std::setfill('0');
				while( !s.eof() ) {
					cerr << std::hex << std::setw(2) << ( s.read() & 0xff ) << " ";
				}
				cerr << std::setfill(' ') << endl;

				throw std::logic_error("Base::open(): Didn`t end correctly.");
			}
		}
		void Base::save()
		{
			structure::StreamWriter s( getFileName() );

			if(!exists_) exists_ = true;

			s.setHeader( getHeader() );
			for(uint i = 0, length = getDescriptor().size(); i < length; i++) {
				s.write( data_[i].serialize() );
			}
		}

		DefineLoader::DefineLoader()
		{
			isArray_.insert("Music");
			isArray_.insert("Sound");
			isArray_.insert("EventState");

			isArray_.insert("Array1D");
			isArray_.insert("Array2D");

			#define PP_insert(arg) \
				defineText_.insert( std::map< RPG2kString, RPG2kString >::value_type( #arg , define::arg ) )
			PP_insert(EventState);
			PP_insert(LcfDataBase);
			PP_insert(LcfMapTree);
			PP_insert(LcfMapUnit);
			PP_insert(LcfSaveData);
			PP_insert(Music);
			PP_insert(Sound);
			#undef PP_insert
		}
		DefineLoader::~DefineLoader()
		{
		}

		DefineLoader& DefineLoader::instance()
		{
			static DefineLoader theDefineLoader;
			return theDefineLoader;
		}

		structure::Map< uint, structure::Descriptor >& DefineLoader::get(RPG2kString const& name)
		{
			if( !defineBuff_.exists(name) ) defineBuff_.addReference( name, load(name) );
			return defineBuff_[name];
		}
		structure::ArrayDefine DefineLoader::getArrayDefine(RPG2kString const& name)
		{
			return get(name)[0].getArrayDefine();
		}

		structure::Map< uint, structure::Descriptor >& DefineLoader::load(RPG2kString const& name)
		{
			structure::Map< uint, structure::Descriptor >& ret = *new structure::Map< uint, structure::Descriptor >();
			std::list< RPG2kString > token;

			if( defineText_.find(name) != defineText_.end() ) {
				cout << "Define open success. name: " << name << ";" << endl;
			} else throw std::runtime_error("Define open failed. name: " + name + ";");

			std::istringstream stream(defineText_.find(name)->second);
			toToken(token, stream);
			parse(ret, token);

			return ret;
		}

		// parser for define Stream

		#define nextToken(curType) pre = curType; continue

		void DefineLoader::parse(
			structure::Map< uint, structure::Descriptor >& res,
			std::list< RPG2kString > const& token
		) {
			bool blockComment = false;
			uint streamComment = 0, line = 1, col = 0, root = 0;
			RPG2kString typeName;

			enum TokenType
			{
				O_INDEX = 0, INDEX, C_INDEX1, C_INDEX2,
				TYPE, NAME, EQUALS, DEFAULT,
				O_STRUCT, C_STRUCT,
				EXP_END,
			} pre = EXP_END;

			std::stack< structure::Map< uint, structure::Descriptor >* > nest;

		// if success continue else error
			for(std::list< RPG2kString >::const_iterator it = token.begin(); it != token.end(); ++it) {
		/*
				if(*it == "\n") clog << "\t\t\tline: " << line << endl;
				else clog << "(" << pre << ")" << *it << " ";
		 */

				if(*it == "\n") { blockComment = false; line++; continue;
				} else if(blockComment) { continue;
				} else if(streamComment) {
					if( (*it == "*") && ( *(++it) == "/" ) ) { streamComment--; }
					continue;
				} else if(*it == "/") {
					if( *(++it) == "*" ) {
						streamComment++; continue;
					} else if(*it == "/") {
						blockComment = true; continue;
					}
				} else if( nest.empty() ) switch(pre) {
					case TYPE: nextToken(NAME);
					case NAME:
						if(*it == ";") {
							res.add(root++, typeName);
							nextToken(EXP_END);
						} else if( isArray(typeName) && (*it == "{") ) {
							structure::Map< uint, structure::Descriptor >* arrayDef =
								new structure::Map< uint, structure::Descriptor >();

							res.add(root++, typeName, *arrayDef);
							nest.push(arrayDef);

							nextToken(O_STRUCT);
						}
						break;
					case C_STRUCT:
						if(*it == ";") { nextToken(EXP_END); } else break;
					case EXP_END:
						typeName = *it; nextToken(TYPE);
					default: break;
				} else switch(pre) {
					case O_INDEX: {
						std::istringstream ss(*it);
						ss >> col;
						if( nest.top()->exists(col) ) {
							cerr << "Defplicated index. ; indexNum = " << col
								<< "; at line : " << line << ";" << endl;
							throw std::runtime_error("Define Stream open error.");
						} else { nextToken(INDEX); }
					}
					case INDEX:
						if(*it == "]") { nextToken(C_INDEX1); } else break;
					case C_INDEX1:
						if(*it == ":") { nextToken(C_INDEX2); } else break;
					case C_INDEX2:
						typeName = *it; nextToken(TYPE);
					case TYPE:
						nextToken(NAME);
					case NAME:
						if(*it == "=") { nextToken(EQUALS);
						} else if(*it == ";") {
							try {
								nest.top()->add(col, typeName,
									*new structure::Map< uint, structure::Descriptor >( getArrayDefine(typeName) )
								);
							} catch(...) {
								nest.top()->add( col, typeName );
							}
							nextToken(EXP_END);
						} else if( (*it == "{") && isArray(typeName) ) {
							structure::Map< uint, structure::Descriptor >* arrayDef =
								new structure::Map< uint, structure::Descriptor >();

							nest.top()->add(col, typeName, *arrayDef);
							nest.push(arrayDef);

							nextToken(O_STRUCT);
						} else break;
					case EQUALS:
						if( isArray(typeName) ) {
							structure::Map< uint, structure::Descriptor >* arrayDef =
								new structure::Map< uint, structure::Descriptor >( getArrayDefine(*it) );
							nest.top()->add(col, typeName, *arrayDef);
						} else {
							nest.top()->add(col, typeName, *it);
						}
						nextToken(DEFAULT);
					case DEFAULT:
						if(*it == ";") { nextToken(EXP_END); } else break;
					case O_STRUCT:
						if(*it == "[") { nextToken(O_INDEX);
						} else if(*it == "}") { nest.pop(); nextToken(C_STRUCT);
						} else break;
					case C_STRUCT:
						if(*it == ";") { nextToken(EXP_END); } else break;
					case EXP_END:
						if(*it == "[") { nextToken(O_INDEX);
						} else if(*it == "}") { nest.pop(); nextToken(C_STRUCT);
						} else break;
					default: break;
				}

				cerr << "Syntax error at line : " << line
					<< "; token = \"" << *it << "\";" << endl;
				throw std::runtime_error("Define Stream open error.");
			}

			if(streamComment != 0) {
				cerr << "Stream comment didin't close correctly." << endl;
				throw std::runtime_error("Define Stream open error.");
			}
		}

		#undef nextToken

		void DefineLoader::toToken(std::list< RPG2kString >& token, std::istream& stream)
		{
			RPG2kString strBuf;

			while(true) {
				int buf = stream.get();

				if(buf == -1) {
					if( !strBuf.empty() ) token.push_back(strBuf);
					break;
				} else if(buf == '\"') {
					if( !strBuf.empty() && (*strBuf.begin() == '\"') ) {
						strBuf.push_back(buf);
						token.push_back(strBuf);
						strBuf.clear();
					} else {
						if( !strBuf.empty() ) {
							token.push_back(strBuf);
							strBuf.clear();
						}
						strBuf.push_back(buf);
					}
				} else if( !strBuf.empty() && (*strBuf.begin() == '\"') ) { strBuf.push_back(buf);
				} else if( std::isalpha(buf) || std::isdigit(buf) || (buf == '_') ) {
					strBuf.push_back(buf);
				} else {
					if( !strBuf.empty() ) {
						token.push_back(strBuf);
						strBuf.clear();
					}
					switch(buf) {
						case ' ': case '\t': case '\r': break;
						default:
							token.push_back( RPG2kString(1, buf) );
							break;
					}
				}
			}
		}
	} // namespace model
} // namespace rpg2kLib
