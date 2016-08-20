#include "Debug.hpp"
#include "Element.hpp"
#include "Encode.hpp"

#include <cctype>


namespace rpg2kLib
{
	// xor shift random number generator
	uint random()
	{
		static uint x=123456789, y=362436069, z=521288629, w=88675123;

		uint t = ( x^(x << 11) );
		x=y; y=z; z=w;
		return ( w = ( w^(w >> 19) ) ^ ( t^(t >> 8) ) );
	}

	SystemString RPG2kString::toSystem() const
	{
		return Encode::instance().toSystem(*this);
	}
	 RPG2kString SystemString::toRPG2k () const
	{
		return Encode::instance().toRPG2k (*this);
	}

	namespace structure
	{
		uint getBERSize(uint32_t num)
		{
			uint ret = 0;
			do {
				ret++;
				num >>= BER_BIT;
			} while(num);
			// clog << "getBERSize() " << num << " : " << ret << endl;
			return ret;
		}

		Binary::Binary(Element& e, Descriptor const& info)
		{
		}
		Binary::Binary(Element& e, Descriptor const& info, Binary const& b)
		{
			*this = b;
		}
		Binary::Binary(Element& e, Descriptor const& info, StreamReader& s)
		{
			throw std::runtime_error("Not supported.");
		}

		bool Binary::isNumber() const
		{
			StreamReader s(*this);

			try { s.getBER(); } catch(...) { return false; }

			return s.eof();
		}
		bool Binary::isString() const
		{
			for(uint i = 0; i < size(); i++) {
				if( std::iscntrl( (*this)[i] )  ) return false;
			}
			try {
				this->toString().toSystem();
				return true;
			} catch(...) {
				return false;
			}
		}

		RPG2kString Binary::toString() const
		{
			return RPG2kString( (char*)pointer(), size() );
		}
		int Binary::toNumber() const
		{
			StreamReader s(*this);

			int32_t ret = s.getBER();
			if( getBERSize(ret) == size() ) return ret;
			else {
				cerr << "Binary::getNumber(): This Binary isn't BER number. "
					<< "size() = " << std::dec << size()
					<< "; getBERSize(" << ret << ") = " << getBERSize(ret)
					<< ";" << endl;
				throw std::runtime_error("Failed converting Binary to number.");
			}
		}
		bool Binary::toBool() const
		{
			if( size() != sizeof(bool) ) {
				cerr << "Binary::getBool(): This Binary isn't bool. "
					<< "size() = " << size() << ";" << endl;
			} else switch( toNumber() ) {
				case false: return false;
				case true : return true ;
				default: break;
			}
			throw std::runtime_error("Failed converting Binary to bool.");
		}
		double Binary::toDouble() const
		{
			if( size() != sizeof(double) ) {
				cerr << "Binary::getDouble(): This Binary isn't double. "
					<< "size() = " << size() << ";" << endl;
				throw std::runtime_error("Failed converting Binary to double.");
			} else return *( (double*)pointer() );
		}

		void Binary::setString(RPG2kString const& str)
		{
			resize( str.size() );
			std::memcpy( this->pointer(), str.c_str(), str.size() );
		}
		void Binary::setNumber(int32_t num)
		{
			resize( getBERSize(num) );
			StreamWriter(*this).setBER(num);
		}
		void Binary::setBool(bool b)
		{
			resize( sizeof(bool) );
			*( this->pointer() ) = b;
		}
		void Binary::setDouble(double d)
		{
			resize( sizeof(double) );
			*( (double*)this->pointer() ) = d;
		}

		uint Binary::serializedSize() const { return size(); }
		void Binary::serialize(StreamWriter& s) const
		{
			s.write(*this);
		}

		void Binary::exchangeEndianIfNeed(uint8_t* dst, uint8_t const* src, uint sizeOf, uint eleNum)
		{
			if(eleNum == 0) return;

			#if RPG2K_IS_BIG_ENDIAN
				for(uint j = 0; j < eleNum; j++) {
					uint8_t const* srcCur = src + sizeOf*j
					uint8_t* dstCur = dst + sizeOf*j;
					for(uint i = 0; i < sizeOf; i++) dstCur[i] = srcCur[sizeOf-i-1];
				}
			#elif RPG2K_IS_LITTLE_ENDIAN
				std::memcpy(dst, src, sizeOf * eleNum);
			#else
				#error unsupported endian
			#endif
		}
	} // namespace structure
} // namespace rpg2kLib
