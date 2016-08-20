#ifndef _INC__RPG2K__MODEL__DESCRIPTOR_HPP
#define _INC__RPG2K__MODEL__DESCRIPTOR_HPP

#include "Define.hpp"
#include "Map.hpp"


namespace rpg2kLib
{
	namespace structure
	{
		class Array1D;
		class Array2D;
		class BerEnum;
		class Element;
		class Event;
		class EventState;
		class Music;
		class Sound;

		typedef RPG2kString string;

		#define PP_refType(func) \
			func(Array1D) func(Array2D) \
			func(Music) func(Sound) \
			func(EventState) \
			func(Event) \
			func(BerEnum) \
			func(Binary)
		#define PP_basicType(func) \
			func(int) \
			func(bool) \
			func(string) \
			func(double)
		#define PP_allType(func) \
			PP_refType(func##Ref) PP_basicType(func)

		class Descriptor
		{
		protected:
			class InstanceInterface
			{
			private:
				RPG2kString const typeName_;
				bool const hasDefault_;
			protected:
				InstanceInterface(RPG2kString const& type, bool hasDef) : typeName_(type), hasDefault_(hasDef) {}
			public:
				InstanceInterface(RPG2kString const& type) : typeName_(type), hasDefault_(false) {}
				virtual ~InstanceInterface() {}

				#define PP_castOperator(type) virtual operator type() const;
				PP_basicType(PP_castOperator)
				#undef PP_castOperator
				virtual operator ArrayDefine() const;

				RPG2kString const& getTypeName() const { return typeName_; }

				bool hasDefault() const { return hasDefault_; }
			}; // class InstanceInterface

			class ArrayInfo;
			class Factory;
		private:
			InstanceInterface& value_;
		public:
			Descriptor(RPG2kString const& type);
			Descriptor(RPG2kString const& type, RPG2kString const& val);
			Descriptor(RPG2kString const& type, ArrayDefine def);

			Descriptor(Descriptor const& src);

			virtual ~Descriptor();

			bool hasDefault() const { return value_.hasDefault(); }

			RPG2kString const& getTypeName() const { return value_.getTypeName(); }
			ArrayDefine getArrayDefine() const { return value_; }

			#define PP_castOperator(type) operator type() const { return value_; }
			PP_basicType(PP_castOperator)
			#undef PP_castOperator

			operator uint() const { return (int)(*this); }
		}; // class Descriptor
	} // namespace structure
} // namespace rpg2kLib

#endif // _INC__RPG2K__MODEL__DESCRIPTOR_HPP
