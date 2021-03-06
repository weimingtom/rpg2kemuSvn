#include <cstdlib>

#include <list>

#include "Array1D.hpp"
#include "Array2D.hpp"
#include "Debug.hpp"
#include "Element.hpp"
#include "Stream.hpp"

// demangling header
#if RPG2K_IS_GCC
	#include <cxxabi.h>
#elif RPG2K_IS_CLANG
#elif RPG2K_IS_MSVC
	extern "C"
	char * _unDName(
		char * outputString,
		const char * name,
		int maxStringLength,
		void * (* pAlloc )(size_t),
		void (* pFree )(void *),
		unsigned short disableFlags
	);
#endif


namespace rpg2kLib
{
	namespace debug
	{
		std::string getError(int errNum)
		{
			const char* message = strerror(errNum);
			switch(errno) {
				case EINVAL: throw std::invalid_argument("Invalid Error Descriptor.");
				default: return message;
			}
		}
		void addAtExitFunction( void (*func)(void) )
		{
			if( atexit(func) != 0 ) throw std::runtime_error("atexit() error.");
		}

		std::string demangleTypeInfo(std::type_info const& info)
		{
			#if (RPG2K_IS_GCC || RPG2K_IS_CLANG)
				int status;
				char* const readable = abi::__cxa_demangle( info.name(), NULL, NULL, &status );
			// errors
				if(!readable) throw std::runtime_error("Demangling failed.");
				switch(status) {
					case -1: throw std::runtime_error("Memory error.");
					case -2: throw std::runtime_error("Invalid name.");
					case -3: throw std::runtime_error("Argument was invalid");
					default: break;
				}
				std::string ret = readable; // char* to string
				std::free(readable);
			#elif RPG2K_IS_MSVC
				char* const readable = _unDName( 0, info.name(), 0, std::malloc, std::free, 0x2800 );
				if(!readable) throw std::runtime_error("Demangling failed.");
				std::string ret = readable; // char* to string
				std::free(readabl);
			#endif

			return ret;
		}

		SystemString const
			Logger::COUT_NAME = ("log" + PATH_SEPR + "out.txt"),
			Logger::CERR_NAME = ("log" + PATH_SEPR + "err.txt");
		char Logger::COUT_BUF[BUF_SIZE], Logger::CERR_BUF[BUF_SIZE];
		std::stack< std::streambuf* > Logger::COUT_FB_STACK, Logger::CERR_FB_STACK;

		void Logger::init()
		{
			std::filebuf* fb;

			fb = new std::filebuf();
			fb->open(COUT_NAME.c_str(), std::ios::out);
			fb->pubsetbuf(COUT_BUF, BUF_SIZE);
			COUT_FB_STACK.push( cout.rdbuf(fb) );

			fb = new std::filebuf();
			fb->open(CERR_NAME.c_str(), std::ios::out);
			fb->pubsetbuf(CERR_BUF, BUF_SIZE);
			CERR_FB_STACK.push( cerr.rdbuf(fb) );

			cout << "Debug start at ";
			cerr << "Debug start at ";
			#if RPG2K_IS_PSP
				cout << "PSP." << endl << endl;
				cerr << "PSP." << endl << endl;
			#else
				cout << "PC." << endl << endl;
				cerr << "PC." << endl << endl;
			#endif
			addAtExitFunction(Logger::dispose);
		}
		void Logger::dispose()
		{
			if( !COUT_FB_STACK.empty() && !CERR_FB_STACK.empty() ) {
				delete /* (std::filebuf*) */ cout.rdbuf( COUT_FB_STACK.top() );
				delete /* (std::filebuf*) */ cerr.rdbuf( CERR_FB_STACK.top() );

				COUT_FB_STACK.pop();
				CERR_FB_STACK.pop();
			}
		}

		void Tracer::printTrace(structure::Element& e, bool info, std::ostream& ostrm)
		{
			std::list< structure::Element* > st;

			for( structure::Element* buf = &e; buf->hasOwner(); buf = &buf->getOwner() ) {
				st.push_back(buf);
			}

			for(std::list< structure::Element* >::const_reverse_iterator i = st.rbegin(); i != st.rend(); ++i) {
				std::string const& ownerType = (*i)->getOwner().getDescriptor().getTypeName();

				ostrm << std::dec << std::setfill(' ');
				ostrm << ownerType
					<< "[" << std::setw(3) << (*i)->getIndex1() << "]";
				if( ownerType == "Array2D" ) ostrm
					<< "[" << std::setw(4) << (*i)->getIndex2() << "]";
				ostrm << ": ";
			}

			if(info) Tracer::printInfo(e, ostrm);
		}

		void Tracer::printInfo(structure::Element& e, std::ostream& ostrm)
		{
			if( e.isDefined() ) {
				RPG2kString type = e.getDescriptor().getTypeName();
				ostrm << type << ": ";

				     if(type == "Binary") printBinary(e, ostrm);
				else if(type == "Event" ) printEvent (e, ostrm);
				else if(type == "bool"  ) printBool  (e.get_bool(), ostrm);
				else if(type == "double") printDouble(e, ostrm);
				else if(type == "string") printString(e, ostrm);
				else if(type == "int"   ) printInt   (e, ostrm);
			} else {
				structure::Binary b = e.serialize();
			// check whether it's empty
				if( b.size() == 0 ) {
					ostrm << endl << "\t" "This data is empty." << endl;
					return;
				}
			// Binary
				ostrm << endl << "\t Binary: ";
				printBinary(b, ostrm);
			// Event
				try {
					structure::Event event(b);
					ostrm << endl << "\t Event: ";
					printEvent(event, ostrm);
				} catch(...) {}
			// BER number
				if( b.isNumber() ) {
					ostrm << endl << "\t" "BER: ";
					printInt(b, ostrm);
				}
			// string
				if( b.isString() ) {
					ostrm << endl << "\t" "string: ";
					printString(b, ostrm);
				}
			}

			ostrm << endl;
		}

		void Tracer::printInt(int val, std::ostream& ostrm)
		{
			ostrm << std::dec << std::setw(10) << std::setfill(' ') << val;
		}
		void Tracer::printBool(bool val, std::ostream& ostrm)
		{
			ostrm << std::boolalpha << val;
		}
		void Tracer::printDouble(double val, std::ostream& ostrm)
		{
			ostrm << std::showpoint << val;
		}
		void Tracer::printString(RPG2kString const& val, std::ostream& ostrm)
		{
			ostrm << "\"" << val.toSystem() << "\"";
		}
		void Tracer::printEvent(structure::Event const& val, std::ostream& ostrm)
		{
			ostrm << std::dec << std::setfill(' ');
			ostrm << "size = " << std::setw(8) << val.serializedSize() << "; data = {";

			for(uint i = 0; i < val.size(); i++) {
				structure::Instruction const& inst = val[i];
				ostrm << endl
					<< "\t\t{ code: " << std::setw(5) << std::dec << inst.code() << ", "
					<< "string: \"" << inst.getString() << "\", "
					<< "integer[" << inst.getArgNum() << "]: "
				;
				if( inst.getArgNum() == 0 ) ostrm << ", ";
				else for(uint j = 0; j < inst.getArgNum(); j++) {
					ostrm << (int32_t)inst[j] << ", ";
				}
				ostrm << "}";
			}

			ostrm << endl << "}";
		}
		void Tracer::printBinary(structure::Binary const& val, std::ostream& ostrm)
		{
			ostrm << std::setfill(' ') << std::dec;
			ostrm << "size = " << std::setw(8) << val.size() << "; data = { ";

			ostrm << std::setfill('0') << std::hex;
			for(uint i = 0; i < val.size(); i++) ostrm << std::setw(2) << (val[i] & 0xff) << " ";

			ostrm << "}";
		}
	} // namespace debug
} // namespace rpg2kLib
