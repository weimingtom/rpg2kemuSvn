#include "DataBase.hpp"


namespace rpg2kLib
{
	namespace model
	{
		DataBase::DataBase(SystemString const& dir)
		: Base(dir)
		{
			load();
		}
		DataBase::DataBase(SystemString const& dir, SystemString const& name)
		: Base(dir, name)
		{
			load();
		}
		DataBase::~DataBase()
		{
		}

		void DataBase::load()
		{
			Base::load();
		// load basic status
			structure::Array2D const& chars = (*this)[11];
			for(structure::Array2D::Iterator it = chars.begin(); it != chars.end(); ++it) {
				// if( !it.second().exists() ) continue;

				charStatus_.insert(
					std::map< uint, std::vector< uint16_t > >::value_type(
						it.first(), it.second()[31].getBinary()
					)
				);
			}
		// load chip infos
			structure::Array2D const& chips = (*this)[20];
			for(structure::Array2D::Iterator it = chips.begin(); it != chips.end(); ++it) {
				if( !it.second().exists() ) continue;

				terrain_.insert(
					std::map< uint, std::vector< uint16_t > >::value_type(
						it.first(), it.second()[3].getBinary()
					)
				);

				std::vector< std::vector< uint8_t > >& dst = chipFlag_[it.first()];
				 dst.push_back( it.second()[4].getBinary() );
				 dst.push_back( it.second()[5].getBinary() );
			}
		}
		void DataBase::save()
		{
		// save basic status
			structure::Array2D& chars = (*this)[11];
			for(structure::Array2D::Iterator it = chars.begin(); it != chars.end(); ++it) {
				if( !it.second().exists() ) continue;

				it.second()[31] = charStatus_.find( it.first() )->second;
			}
		// save chip info
			structure::Array2D const& chips = (*this)[20];
			for(structure::Array2D::Iterator it = chips.begin(); it != chips.end(); ++it) {
				if( !it.second().exists() ) continue;

				it.second()[3] = terrain_.find( it.first() )->second;
				it.second()[4] = chipFlag_.find( it.first() )->second[0];
				it.second()[5] = chipFlag_.find( it.first() )->second[1];
			}

			Base::save();
		}

		uint DataBase::getBasicStatus(int charID, int level, ParamType t) const
		{
			std::vector< uint16_t > const& data = charStatus_.find(charID)->second;
			return data.at(data.size() / PRM_END * t + --level);
		}
		std::vector< uint8_t >& DataBase::lowerChipFlag(uint id)
		{
			return chipFlag_.find(id)->second.at(CHIP_LOWER);
		}
		std::vector< uint8_t >& DataBase::upperChipFlag(uint id)
		{
			return chipFlag_.find(id)->second.at(CHIP_UPPER);
		}
		std::vector< uint16_t >& DataBase::terrain(uint id)
		{
			return terrain_.find(id)->second;
		}
	} // namespace model
} // namespace rpg2kLib
