#include "MapUnit.hpp"


namespace rpg2kLib
{
	namespace model
	{
		MapUnit::MapUnit(SystemString const& dir, SystemString const& name)
		: Base(dir, name), id_(0)
		{
			load();
		}
		MapUnit::MapUnit(SystemString const& dir, uint id)
		: Base(dir, ""), id_(id)
		{
			std::ostringstream ss;
			ss << "Map" << std::setfill('0') << std::setw(4) << id << ".lmu";
			setFileName( ss.str() );

			checkExists();

			load();
		}
		void MapUnit::load()
		{
			if( (id_ < ID_MIN) || (MAP_UNIT_MAX < id_) ) throw std::invalid_argument("mapID");

			Base::load();

			lower_ = (*this)[71].getBinary();
			upper_ = (*this)[72].getBinary();

			width_  = (*this)[2];
			height_ = (*this)[3];
		}

		MapUnit::~MapUnit()
		{
		}

		void MapUnit::save()
		{
			(*this)[71] = lower_;
			(*this)[72] = upper_;

			(*this)[2] = width_ ;
			(*this)[3] = height_;

			Base::save();
		}

		int MapUnit::chipIDLw(uint x, uint y) const
		{
			if( x >= getWidth() ) throw std::range_error("x is too big");
			else if( y >= getHeight() ) throw std::range_error("y is too big");
			else return lower_[getWidth()*y + x];
		}
		int MapUnit::chipIDUp(uint x, uint y) const
		{
			if( x >= getWidth() ) throw std::range_error("x is too big");
			else if( y >= getHeight() ) throw std::range_error("y is too big");
			else return upper_[getWidth()*y + x];
		}
	} // namespace model
} // namespace rpg2kLib 
