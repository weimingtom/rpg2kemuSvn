#include "MapTree.hpp"


namespace rpg2kLib
{
	namespace model
	{
		MapTree::MapTree(SystemString const& dir)
		: Base(dir)
		{
			load();
		}
		MapTree::MapTree(SystemString const& dir, SystemString const& name)
		: Base(dir, name)
		{
			load();
		}
		void MapTree::load()
		{
			Base::load();
		/*
			BerEnum& existSrc = getExist();
			for(uint i = 0; i < existSrc.size(); i++) {
				exists_.insert( map< uint, bool >::value_type( existSrc[i], true ) );
			}
		 */
		}

		MapTree::~MapTree()
		{
		}

		/*
		bool MapTree::exists(uint mapID)
		{
			if( exists_.find(mapID) != exists_.end() ) return exists_[mapID];
			else return false;
		}
		 */

		bool MapTree::canTeleport(uint mapID) const
		{
			switch( (*this)[mapID][31].get_int() )
			{
				case 0: return canTeleport( (*this)[mapID][2] );
				case 1: return true ;
				case 2: return false;
				default: throw std::range_error("Cannot get teleport restriction.");
			}
		}
		bool MapTree::canEscape(uint mapID) const
		{
			switch( (*this)[mapID][32].get_int() )
			{
				case 0: return canTeleport( (*this)[mapID][2] );
				case 1: return true ;
				case 2: return false;
				default: throw std::range_error("Cannot get teleport restriction.");
			}
		}
		bool MapTree::canSave(uint mapID) const
		{
			switch( (*this)[mapID][33].get_int() )
			{
				case 0: return canTeleport( (*this)[mapID][2] );
				case 1: return true ;
				case 2: return false;
				default: throw std::range_error("Cannot get teleport restriction.");
			}
		}
	} // namespace model
} // namespace rpg2kLib
