#ifndef _INC__RPG2K__MODEL__ELEMENT_HPP
#define _INC__RPG2K__MODEL__ELEMENT_HPP

#include "Descriptor.hpp"
#include "Stream.hpp"


namespace rpg2kLib
{
	namespace structure
	{
		class Element
		{
		protected:
			class InstanceInterface
			{
			private:
				Element& owner_;
				const Descriptor* descriptor_;
				Binary binData_;

				bool exists_;
			protected:
				Binary& getBinary() { return binData_; }
				Element& toElement() { return owner_; }
			public:
				InstanceInterface(Element& e);
				InstanceInterface(Element& e, Binary const& b);

				InstanceInterface(Element& e, Descriptor const& info);
				InstanceInterface(Element& e, Descriptor const& info, Binary const& b);
				InstanceInterface(Element& e, Descriptor const& info, StreamReader& s);

				bool isDefined() const { return descriptor_ != NULL; }

				virtual ~InstanceInterface() {}

				bool exists() const { return exists_; }
				void substantiate() { exists_ = true; }

				Descriptor const& getDescriptor() const;

				virtual uint serializedSize() const { return binData_.size(); }
				virtual void serialize(StreamWriter& s) const { s.write(binData_); }

				#define PP_castOperator(type) virtual operator type&();
				#define PP_castOperatorRef(type) virtual operator type&();
				PP_allType(PP_castOperator)
				#undef PP_castOperator
				#undef PP_castOperatorRef
			};

			class Factory;
		private:
			Element* owner_;
			static int const INVALID_INDEX = -1;
			int const index1_, index2_;

			InstanceInterface& instance_;
		protected:
			void clear();

			void init();
			void init(Element& e);
		public:
			Element(Element const& e);
			virtual ~Element();

			Element(Descriptor const& info);
			Element(Descriptor const& info, Binary const& b);
			Element(Descriptor const& info, StreamReader& s);

			Element(Array1D const& owner, uint index);
			Element(Array1D const& owner, uint index , Binary const& b);
			Element(Array2D const& owner, uint index1, uint index2);
			Element(Array2D const& owner, uint index1, uint index2, Binary const& b);

			bool exists() const { return instance_.exists(); }
			void substantiate();

			bool isDefined() const { return instance_.isDefined(); }

			Descriptor const& getDescriptor() const { return instance_.getDescriptor(); }

			bool hasOwner() const { return owner_ != NULL; }
			Element& getOwner() const;

			uint getIndex1() const;
			uint getIndex2() const;

			/*
			 * use assignment operator instead of "Element::getXXXX() = YYYY;"
			 * because it won't call substantiate and will be ignored at Model::save()
			 */
			#define PP_castOperator(type) \
				operator type&() const { return instance_; } \
				type& get_##type() const { return instance_; } \
				type& operator =(type const& src); \
				void set_##type(type const& src) { (*this) = src; }
			#define PP_castOperatorRef(type) \
				operator type&() const { return instance_; } \
				type& get##type() const { return instance_; } \
				type& operator =(type const& src); \
				void set##type(type const& src) { (*this) = src; }
			PP_allType(PP_castOperator)
			#undef PP_castOperator
			#undef PP_castOperatorRef

			operator uint&() const { return reinterpret_cast< uint& >( get_int() ); }
			uint& operator =(uint num) { *this = (int)num; return *this; }
			uint& get_uint() const { return reinterpret_cast< uint& >( get_int() ); }

			template< typename T >
			Binary& operator =(std::vector< T > const& src)
			{
				this->substantiate();
				this->getBinary() = src;
				return this->getBinary();
			}

			// only assigns the value in "instance_"
			Element& operator =(Element const& src);

			uint serializedSize() const { return instance_.serializedSize(); }
			Binary serialize() const { return structure::serialize(instance_); }
			void serialize(StreamWriter& s) const { instance_.serialize(s); }
		}; // class Element

		#define PP_operator(retType, op, type) \
			inline retType operator op(Element& e, type in) { return static_cast< type >(e) op in; } \
			inline retType operator op(type in, Element& e) { return in op static_cast< type >(e); }
		PP_operator(bool, ==, RPG2kString const&)
		PP_operator(bool, !=, RPG2kString const&)
		#undef PP_operator

		class BerEnum : public std::vector< uint >
		{
		private:
			Binary binData_;
		protected:
			void init(StreamReader& s);
		public:
			BerEnum(Element& e, Descriptor const& info) {}
			BerEnum(Element& e, Descriptor const& info, StreamReader& s);
			BerEnum(Element& e, Descriptor const& info, Binary const& b);

			virtual ~BerEnum() {}

			uint serializedSize() const;
			void serialize(StreamWriter& s) const;
		}; // class BerEnum
	} // namespace structure
} // namespace rpg2kLib

#endif
