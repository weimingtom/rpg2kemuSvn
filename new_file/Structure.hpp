#ifndef _INC__RPG2K__STRUCTURE_HPP
#define _INC__RPG2K__STRUCTURE_HPP

#include "Define.hpp"
#include "Map.hpp"

#include <iomanip>
#include <iostream>
#include <sstream>
#include <stdexcept>
#include <string>
#include <vector>

#include <climits>
#include <cstring>


namespace rpg2kLib
{
	static const uint STREAM_BUFFER_SIZE = 512;
	#define rpg2kLib_setStreamBuffer(name) \
		char name##Buf[rpg2kLib::STREAM_BUFFER_SIZE]; \
		name.rdbuf()->pubsetbuf(name##Buf, rpg2kLib::STREAM_BUFFER_SIZE)

	namespace structure
	{
		class StreamReader;
		class StreamWriter;

		static uint const
			BER_BIT  = (CHAR_BIT-1),
			BER_SIGN = 0x01 << BER_BIT,
			BER_MASK = BER_SIGN - 1;
		extern uint getBERSize(uint num);

		class Element;
		class Descriptor;

		class Binary : public std::vector< uint8_t >
		{
		private:
			static void exchangeEndianIfNeed(uint8_t* dst, const uint8_t* src, uint sizeOf, uint eleNum);
		public:
			Binary(Element& e, Descriptor const& info);
			Binary(Element& e, Descriptor const& info, StreamReader& f);
			Binary(Element& e, Descriptor const& info, Binary const& b);

			Binary() : std::vector< uint8_t >() {}
			explicit Binary(uint size) : std::vector< uint8_t >(size) {}
			explicit Binary(uint8_t* data, uint size) : std::vector< uint8_t >(data, data + size) {}
			Binary(Binary const& b) : std::vector< uint8_t >(b) {}
			Binary(RPG2kString str) { setString(str); }

			const uint8_t* pointer(uint index = 0) const { return &((*this)[index]); }
			uint8_t* pointer(uint index = 0) { return &((*this)[index]); }

			bool isNumber() const;
			bool isString() const;
		// setter
			void setString(RPG2kString const& str);
			void setNumber(int32_t num);
			void setBool(bool b);
			void setDouble(double d);
		// converter
			RPG2kString toString() const;
			int    toNumber() const;
			bool   toBool  () const;
			double toDouble() const;
		// operator wrap of converter
			operator RPG2kString() const { return toString(); }
			operator int  () const { return toNumber(); }
			operator bool  () const { return toBool  (); }
			operator double() const { return toDouble(); }
		// operator wrap of setter
			Binary& operator =(RPG2kString const& src) { setString(src); return *this; }
			Binary& operator =(int    src) { setNumber(src); return *this; }
			Binary& operator =(bool   src) { setBool  (src); return *this; }
			Binary& operator =(double src) { setDouble(src); return *this; }

			uint serializedSize() const;
			void serialize(StreamWriter& s) const;
			// Binary serialize() const { return *this; }

			template< typename T >
			void assign(std::vector< T > const& src)
			{
				resize( sizeof(T) * src.size() );
				exchangeEndianIfNeed(
					this->pointer(),
					reinterpret_cast< const uint8_t* >( &src.front() ),
					sizeof(T), src.size()
				);
			}

			template< typename T >
			std::vector< T > convert() const
			{
				if( ( size() % sizeof(T) ) != 0 ) throw std::runtime_error("Convert failed.");

				uint length = this->size() / sizeof(T);
				std::vector< T > output(length);

				exchangeEndianIfNeed(
					reinterpret_cast< uint8_t* >( &output.front() ),
					this->pointer(),
					sizeof(T), length
				);

				return output;
			}

			bool operator ==(Binary const& src) const
			{
				return
					( this->size() == src.size() ) &&
					( std::memcmp( this->pointer(), src.pointer(), this->size() ) == 0 );
			}
			bool operator !=(Binary const& src) const { return !( (*this) == src ); }

			template< typename T >
			Binary(std::vector< T > const& input) { assign(input); }
			template< typename T >
			Binary& operator =(std::vector< T > const& input) { assign(input); return *this; }
			template< typename T >
			operator std::vector< T >() const { return convert< T >(); }
		}; // class Binary
	} // namespace structure
} // namespace rpg2kLib

#endif // _INC__RPG2K__STRUCTURE_HPP
