#include "Debug.hpp"
#include "Stream.hpp"


namespace rpg2kLib
{
	namespace structure
	{
		StreamWriter::StreamWriter(StreamInterface& imp, bool autoRelease)
		: implement_(imp), autoRelease_(autoRelease)
		{
		}
		StreamReader::StreamReader(StreamInterface& imp, bool autoRelease)
		: implement_(imp), autoRelease_(autoRelease)
		{
		}
		StreamWriter::StreamWriter(SystemString const& name)
		: implement_( *new FileWriter(name) ), autoRelease_(true)
		{
		}
		StreamReader::StreamReader(SystemString const& name)
		: implement_( *new FileReader(name) ), autoRelease_(true)
		{
		}
		StreamWriter::StreamWriter(Binary& bin)
		: implement_( *new BinaryWriter(bin) ), autoRelease_(true)
		{
		}
		StreamReader::StreamReader(Binary const& bin)
		: implement_( *new BinaryReader(bin) ), autoRelease_(true)
		{
		}
		StreamWriter::~StreamWriter()
		{
			if(autoRelease_) delete &implement_;
		}
		StreamReader::~StreamReader()
		{
			if(autoRelease_) delete &implement_;
		}

		uint8_t StreamReader::read()
		{
			if( eof() ) throw std::out_of_range("Reached end of stream");
			else return implement_.read();
		}
		uint StreamReader::read(uint8_t* data, uint size)
		{
			if( this->size() < ( tell()+size ) ) throw std::out_of_range("Reached end of stream");
			else return implement_.read(data, size);
		}
		uint StreamReader::read(Binary& b)
		{
			if( this->size() < ( tell()+b.size() ) ) throw std::out_of_range("Reached end of stream");
			else return implement_.read( b.pointer(), b.size() );
		}

		void StreamWriter::write(uint8_t data)
		{
			if( this->size() <= tell() ) resize( tell() + sizeof(uint8_t) );
			implement_.write(data);
		}
		uint StreamWriter::write(const uint8_t* data, uint size)
		{
			if( this->size() < ( tell()+size ) ) resize( tell()+size );
			return implement_.write(data, size);
		}
		uint StreamWriter::write(Binary const& b)
		{
			if( this->size() < ( tell()+b.size() ) ) resize( tell()+b.size() );
			return implement_.write( b.pointer(), b.size() );
		}

		uint StreamReader::getBER()
		{
			uint32_t ret = 0;
			uint8_t data;
		// extract
			do {
				data = this->read();
				ret = (ret << BER_BIT) | (data & BER_MASK);
			} while(data > BER_SIGN);
		// result
			return ret;
		}
		uint StreamWriter::setBER(uint num)
		{
			// BER output buffer
			uint8_t buff[ ( sizeof(num) * CHAR_BIT ) / BER_BIT + 1];
			uint size = getBERSize(num), index = size;
			// uint numBack = num;
		// set data
			buff[--index] = num & BER_MASK; // BER terminator
			num >>= BER_BIT;
			while(num) {
				buff[--index] = (num & BER_MASK) | BER_SIGN;
				num >>= BER_BIT;
			}
/*
			clog << numBack << " = " << size << " :";
			clog << std::setfill('0') << std::hex;
			for(uint i = 0; i < size; i++) clog << " " << std::setw(2) << (buff[i] & 0xff);
			clog << std::setfill(' ') << std::dec;
			clog << ";" << endl;
 */
		// write data
			write(buff, size);
			return size;
		}
		bool StreamReader::checkHeader(RPG2kString const& header)
		{
			Binary buf;
			return static_cast< RPG2kString >( get(buf) ) == header;
		}

		FileInterface::FileInterface(SystemString const& filename, const char* mode)
		: filePointer_( std::fopen( filename.c_str(), mode ) ), name_(filename)
		{
			if(filePointer_ == NULL) {
				throw std::runtime_error( "Error at Stream.open(" + name() + "): " + debug::getError(errno) );
			}

			size_ = seekFromEnd(0);
			seekFromSet(0);
		}
		#define seekDefine(name1, name2) \
			uint FileInterface::seekFrom##name1(uint val) \
			{ \
				if( fseek(filePointer_, val, SEEK_##name2) != 0 ) throw std::runtime_error( debug::getError(errno) ); \
				return tell(); \
			}
		seekDefine(Set, SET)
		seekDefine(Cur, CUR)
		seekDefine(End, END)
		#undef seekDefine
		FileReader::FileReader(SystemString const& name)
		: FileInterface(name, "rb")
		{
		}
		FileReader::~FileReader()
		{
		}
		FileWriter::FileWriter(SystemString const& name)
		: FileInterface(name, "w+b")
		{
		}
		FileWriter::~FileWriter()
		{
		}
		uint FileReader::read(uint8_t* data, uint size)
		{
			return std::fread( data, sizeof(uint8_t), size, getFilePointer() );
		}
		uint8_t FileReader::read()
		{
			int ret = std::fgetc( getFilePointer() );

			if( ret != EOF ) return ret;
			else throw std::runtime_error( debug::getError(errno) );
		}
		uint FileWriter::write(const uint8_t* data, uint size)
		{
			return std::fwrite( data, sizeof(uint8_t), size, getFilePointer() );
		}
		void FileWriter::write(uint8_t data)
		{
			if( std::fputc( (int)data, getFilePointer() ) == EOF ) {
				throw std::runtime_error( debug::getError(errno) );
			}
		}

		FileInterface::~FileInterface()
		{
			if( fclose(filePointer_) == EOF ) {
				throw std::runtime_error( "Error at Stream(" + name() + ").close() :" + debug::getError(errno) );
			}
		}

		BinaryReader::BinaryReader(Binary const& bin)
		: seek_(0), binary_(bin)
		{
		}
		BinaryWriter::BinaryWriter(Binary& bin)
		: seek_(0), binary_(bin)
		{
		}
		BinaryWriter::~BinaryWriter()
		{
			resize( tell() );
		}

		#define PP_binarySeek(type) \
			uint Binary##type::seekFromSet(uint val) \
			{ \
				if( val > size() ) getSeek() = size(); \
				else getSeek() = val; \
				 \
				return getSeek(); \
			} \
			uint Binary##type::seekFromCur(uint val) \
			{ \
				if( (getSeek() + val) > size() ) getSeek() = size(); \
				else getSeek() += val; \
				 \
				return getSeek(); \
			} \
			uint Binary##type::seekFromEnd(uint val) \
			{ \
				uint len = size(); \
				 \
				if( (len - val) > len ) getSeek() = 0; \
				else getSeek() = len - val - 1; \
				 \
				return getSeek(); \
			}
		PP_binarySeek(Reader)
		PP_binarySeek(Writer)
		#undef PP_binarySeek

		uint8_t BinaryReader::read()
		{
			return getBinary()[getSeek()++];
		}
		uint BinaryReader::read(uint8_t* data, uint size)
		{
			uint& seek = getSeek();

			memcpy( data, getBinary().pointer(seek), size );
			seek += size;

			return size;
		}
		void BinaryWriter::write(uint8_t data)
		{
			getBinary().pointer()[getSeek()++] = data;
		}
		uint BinaryWriter::write(const uint8_t* data, uint size)
		{
			uint& seek = getSeek();

			memcpy( getBinary().pointer(seek), data, size );
			seek += size;

			return size;
		}
	} // namespace structure
} // namespace rpg2kLib
