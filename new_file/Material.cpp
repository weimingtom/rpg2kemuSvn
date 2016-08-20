#include "Main.hpp"
#include "Material.hpp"


namespace rpg2kLib
{
	Material::Material(Main& m)
	: owner_(m)
	{
		#define PP_addElement(name) \
			directories_.insert( std::map< Material::Type, SystemString >::value_type(Material::name, #name) );
		PP_allMaterial(PP_addElement)
		#undef PP_addElement
		resources_.push_back( m.getGameDir() );
		resources_.push_back( m.getRTPDir () );
	}
	Material::~Material()
	{
	}
	SystemString const& Material::getName(Material::Type t) const
	{
		if( directories_.find(t) != directories_.end() ) return directories_.find(t)->second;
		else throw std::invalid_argument("Invalid type value");
	}
	SystemString const& Material::getResource(Material::Resource t) const
	{
		return resources_.at(t);
	}
	SystemString Material::getFileName(Material::Resource dir, Material::Type type, RPG2kString const& name, SystemString const& ext) const
	{
		return (getResource(dir) + PATH_SEPR + getName(type) + PATH_SEPR + name.toSystem() + "." + ext);
	}
} // namespace rpg2kLib
