#ifndef _INC__RPG2K__MODEL__ARRAY_1D_HPP
#define _INC__RPG2K__MODEL__ARRAY_1D_HPP

#include "Define.hpp"
#include "Map.hpp"


namespace rpg2kLib
{
	namespace structure
	{
		class Array2D;
		class Element;
		class EventState;
		class Music;
		class Sound;
		class StreamReader;
		class StreamWriter;

		class Array1D
		{
		private:
			Map< uint, Element > data_;
			ArrayDefine arrayDefine_;

			Element* this_;

			bool exists_;
			Array2D* owner_;
			uint index_;

			static uint const ARRAY_1D_END = 0;
		protected:
			Array1D();

			void init(StreamReader& s);
		public:
			typedef Map< uint, Element >::Iterator Iterator;
			typedef Map< uint, Element >::ReverseIterator ReverseIterator;

			Array1D(Array1D const& array);

			Array1D(ArrayDefine info);
			Array1D(ArrayDefine info, StreamReader& s);
			Array1D(ArrayDefine info, Binary const& b);

			Array1D(Element& e, Descriptor const& info);
			Array1D(Element& e, Descriptor const& info, StreamReader& s);
			Array1D(Element& e, Descriptor const& info, Binary const& b);

			Array1D(Array2D& owner, uint index);
			Array1D(Array2D& owner, uint index, StreamReader& f);

			virtual ~Array1D();

			Array1D& operator =(Array1D const& src);

		// throws exception if it's not a part of Array2D
			bool isArray2D() const { return owner_ != NULL; }
			uint getIndex() const;
			Array2D& getOwner() const;

			Element& operator [](uint index);
			Element const& operator [](uint index) const
			{
				return const_cast< Array1D& >(*this)[index];
			}

			bool exists() const;
			bool exists(uint index) const;

			uint getExistence() const;
			uint serializedSize() const;
			void serialize(StreamWriter& s) const;

			void substantiate();
			void remove(uint index) { data_.remove(index); }
			void clear() { exists_ = false; data_.clear(); }

			ArrayDefine getArrayDefine() const { return arrayDefine_; }

			bool isElement() const;
			Element& toElement() const;

			Iterator begin() const { return data_.begin(); }
			Iterator end  () const { return data_.end  (); }
			ReverseIterator rbegin() const { return data_.rbegin(); }
			ReverseIterator rend  () const { return data_.rend  (); }
		}; // class Array1D
	} // namespace structure
} // namespace rpg2kLib

#endif
