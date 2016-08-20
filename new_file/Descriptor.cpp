#include "Descriptor.hpp"
#include "Structure.hpp"


namespace rpg2kLib
{
	namespace structure
	{
		class Descriptor::ArrayInfo : public InstanceInterface
		{
		private:
			ArrayDefine arrayDefine_;
		public:
			ArrayInfo(RPG2kString const& type, ArrayDefine info);
			virtual ~ArrayInfo();

			virtual operator ArrayDefine() const { return arrayDefine_; }
		}; // ArrayInfo

		class Descriptor::Factory
		{
		public:
			class FactoryInterface
			{
			public:
				virtual InstanceInterface& create(RPG2kString const& type, RPG2kString const& val) = 0;
			}; // class FactoryInterface

			template< typename T >
			class FactoryInstance : public FactoryInterface
			{
			public:
				class Value : public InstanceInterface
				{
				private:
					T const data_;
				public:
					Value(Value const& src) : InstanceInterface( src.getTypeName(), true ), data_(src.data_) {}
					Value(RPG2kString const& type, T val) : InstanceInterface(type, true), data_(val) {}
					virtual ~Value() {}

					virtual operator T() const { return data_; }
				}; // class Value

				T convert(RPG2kString const& val)
				{
					std::istringstream ss(val);
					T ret;

					// clog << "\"" << val << "\" : ";

					ss.exceptions( /* std::ios_base::eofbit | */ std::ios_base::failbit | std::ios_base::badbit );
					ss >> std::boolalpha >> ret;

					// clog << std::boolalpha << ret << endl;

					return ret;
				}
			public:
				virtual InstanceInterface& create(RPG2kString const& type, RPG2kString const& val)
				{
					return *new Value( type, convert(val) );
				}
			}; // class FactoryInstance
		private:
			Map< RPG2kString, FactoryInterface > factory_;
		protected:
			Factory()
			{
				#define PP_enum(type) factory_.addReference( #type, *new FactoryInstance< type >() );
				PP_basicType(PP_enum)
				#undef PP_enum
			}
			Factory(Factory const& i);
		public:
			static Factory& instance()
			{
				static Factory theFactory;
				return theFactory;
			}

			InstanceInterface& create(RPG2kString const& type)
			{
				return *new InstanceInterface(type);
			}
			InstanceInterface& create(RPG2kString const& type, RPG2kString const& val)
			{
				return factory_[type].create(type, val);
			}
			InstanceInterface& create(RPG2kString const& type, ArrayDefine def)
			{
				return *new ArrayInfo(type, def);
			}

			InstanceInterface& copy(Descriptor::InstanceInterface const& src)
			{
				if( !src.hasDefault() ) return *new InstanceInterface( src.getTypeName() );
				#define PP_enum(type) \
					else if( src.getTypeName() == #type ) return *new FactoryInstance< type >::Value( (FactoryInstance< type >::Value&)src );
				PP_basicType(PP_enum)
				#undef PP_enum
				else return *new ArrayInfo( src.getTypeName(), src );
			}
		}; // class Factory

		template< >
		RPG2kString Descriptor::Factory::FactoryInstance< RPG2kString >::convert(RPG2kString const& val)
		{
			if( (val.length() >= 2) && (*val.begin() == '\"') && (*val.rbegin() == '\"') ) {
				return RPG2kString( ++val.begin(), --val.end() );
			} else return val;
		}

		Descriptor::Descriptor(RPG2kString const& type)
		: value_( Factory::instance().create(type) )
		{
		}
		Descriptor::Descriptor(RPG2kString const& type, RPG2kString const& val)
		: value_( Factory::instance().create(type, val) )
		{
		}
		Descriptor::Descriptor(RPG2kString const& type, ArrayDefine def)
		: value_( Factory::instance().create(type, def) )
		{
		}

		Descriptor::Descriptor(Descriptor const& src)
		: value_( Factory::instance().copy(src.value_) )
		{
		}

		Descriptor::~Descriptor()
		{
			delete &value_;
		}

		Descriptor::ArrayInfo::ArrayInfo(RPG2kString const& type, ArrayDefine info)
		: InstanceInterface(type, true), arrayDefine_(info)
		{
		}
		Descriptor::ArrayInfo::~ArrayInfo()
		{
		}

		#define PP_castOperator(type) \
			Descriptor::InstanceInterface::operator type()  const\
			{ \
				throw std::runtime_error( "Not supported at type: " + getTypeName() ); \
			}
		PP_basicType(PP_castOperator)
		#undef PP_castOperator

		Descriptor::InstanceInterface::operator ArrayDefine() const
		{
			throw std::runtime_error( "Not supported at type: " + getTypeName() );
		}
	} // namespace structure
} // namespace rpg2kLib
