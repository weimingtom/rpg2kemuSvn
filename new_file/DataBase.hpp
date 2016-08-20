#ifndef _INC__RPG2K__MODEL__data__BASE_HPP
#define _INC__RPG2K__MODEL__data__BASE_HPP

#include "Model.hpp"

namespace rpg2kLib
{
	namespace model
	{
		class DataBase : public Base
		{
		private:
			std::map< uint, std::vector< uint16_t > > charStatus_;

			std::map< uint, std::vector< uint16_t > > terrain_;
			std::map< uint, std::vector< std::vector< uint8_t > > > chipFlag_;
		protected:
			virtual void load();

			virtual RPG2kString getHeader() const { return "LcfDataBase"; }
			virtual SystemString defaultName() const { return "RPG_RT.ldb"; }
		public:
			DataBase(SystemString const& dir);
			DataBase(SystemString const& dir, SystemString const& name);
			virtual ~DataBase();

			uint getBasicStatus(int charID, int level, ParamType t) const;

			virtual void save();

			structure::Array2D& character() const { return (*this)[11]; }

			std::vector< uint8_t >& lowerChipFlag(uint id);
			std::vector< uint8_t >& upperChipFlag(uint id);
			std::vector< uint16_t >& terrain(uint id);
		};
	} // namespace model
} // namespace rpg2kLib

#endif
