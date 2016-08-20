#include "Array1D.hpp"
#include "Array2D.hpp"
#include "Element.hpp"
#include "Debug.hpp"


namespace rpg2kLib
{
	namespace structure
	{
		Array1D::Array1D(Array1D const& array)
		: data_(array.data_), arrayDefine_(array.arrayDefine_), this_(array.this_)
		, exists_(array.exists_), owner_(array.owner_), index_(array.index_)
		{
		}

		Array1D::Array1D(ArrayDefine info)
		: arrayDefine_(info), this_(NULL)
		{
			exists_ = false;
		}
		Array1D::Array1D(ArrayDefine info, StreamReader& s)
		: arrayDefine_(info), this_(NULL)
		{
			init(s);
		}
		Array1D::Array1D(ArrayDefine info, Binary const& b)
		: arrayDefine_(info), this_(NULL)
		{
			StreamReader s(b);
			init(s);
		}

		Array1D::Array1D(Element& e, Descriptor const& info)
		: arrayDefine_( info.getArrayDefine() ), this_(&e)
		{
			owner_ = NULL;
			exists_ = false;
		}
		Array1D::Array1D(Element& e, Descriptor const& info, StreamReader& s)
		: arrayDefine_( info.getArrayDefine() ), this_(&e)
		{
			init(s);
		}
		Array1D::Array1D(Element& e, Descriptor const& info, Binary const& b)
		: arrayDefine_( info.getArrayDefine() ), this_(&e)
		{
			StreamReader s(b);
			init(s);
		}
		Array1D::Array1D(Array2D& owner, uint index)
		: arrayDefine_( owner.getArrayDefine() ), this_(NULL)
		{
			exists_ = false;
			index_ = index;
			owner_ = &owner;
		}
		Array1D::Array1D(Array2D& owner, uint index, StreamReader& s)
		: arrayDefine_( owner.getArrayDefine() ), this_(NULL)
		{
			exists_ = true;
			index_ = index;
			owner_ = &owner;

			Binary b;

			while(true) {
				uint index2 = s.getBER();

				if(index2 == ARRAY_1D_END) break;
				else data_.add( index2, owner, index, index2, s.get(b) );
			}
		}
		void Array1D::init(StreamReader& s)
		{
			owner_ = NULL;
			exists_ = true;

			Binary b;

			while(true)
			{
				uint index = s.getBER();

				if(index == ARRAY_1D_END) break;
				else data_.add( index, *this, index, s.get(b) );

				if( !toElement().hasOwner() && s.eof() ) return;
			}

			if( !s.eof() ) throw std::runtime_error("Didn't end with EOF.");
		}

		Array1D::~Array1D()
		{
		}

		bool Array1D::isElement() const
		{
			return (this_ != NULL) || ( isArray2D() && owner_->isElement() );
		}

		Element& Array1D::toElement() const
		{
			if( isElement() ) {
				if( isArray2D() ) return owner_->toElement();
				else return *this_;
			} else throw std::runtime_error("Not Element.");
		}

		Array1D& Array1D::operator =(Array1D const& src)
		{
			exists_ = src.exists_;
			owner_ = src.owner_;
			// index_ = src.index_;

			data_ = src.data_;
			return *this;
		}

		// #define TRACE_ALL

		Element& Array1D::operator [](uint index)
		{
			if( data_.exists(index) ) return data_[index];
			else {
				if( isArray2D() ) data_.add( index, *owner_, index_, index );
				else data_.add( index, *this, index );
				return data_[index];
			}
		}

		uint Array1D::getExistence() const
		{
			uint ret = 0;
			for(Iterator it = begin(); it != end(); ++it) {
				if( it.second().exists() ) ret++;
			}
			return ret;
		}
		uint Array1D::serializedSize() const
		{
			uint ret = 0;

			for(Iterator it = begin(); it != end(); ++it) {
				if( !it.second().exists() ) continue;

				uint index = it.first();
				ret += getBERSize(index);
				uint size = it.second().serializedSize();
				ret += getBERSize(size);
				ret += size;
			}

			if( toElement().hasOwner() ) ret += getBERSize(ARRAY_1D_END);

			return ret;
		}
		void Array1D::serialize(StreamWriter& s) const
		{
			s.add( serializedSize() );

			for(Iterator it = begin(); it != end(); ++it) {
				if( !it.second().exists() ) continue;

				s.setBER( it.first() );
				s.setBER( it.second().serializedSize() );
				it.second().serialize(s);
			}

			if( toElement().hasOwner() ) s.setBER(ARRAY_1D_END);
		}

		uint Array1D::getIndex() const
		{
			if( isArray2D() ) return index_;
			else throw std::runtime_error("This Array1D isn't a part of Array2D.");
		}

		bool Array1D::exists() const
		{
			if( isArray2D() ) return exists_;
			else throw std::runtime_error("This Array1D isn't a part of Array2D.");
		}

		Array2D& Array1D::getOwner() const
		{
			if( isArray2D() ) return *owner_;
			else throw std::runtime_error("This Array1D isn't a part of Array2D.");
		}

		void Array1D::substantiate()
		{
			if( isArray2D() ) {
				toElement().substantiate();
				exists_ = true;
			} else throw std::runtime_error("This Array1D isn't a part of Array2D.");
		}
		bool Array1D::exists(uint index) const
		{
			return data_.exists(index) ? data_[index].exists() : false;
		}
	} // namespace structure
} // namespace rpg2kLib
