#ifndef _INC__RPG2K__MODEL_HPP
#define _INC__RPG2K__MODEL_HPP

#include <fstream>
#include <list>
#include <map>
#include <set>

#include "Array1D.hpp"
#include "Array2D.hpp"
#include "Element.hpp"
#include "Map.hpp"


namespace rpg2kLib
{
	namespace model
	{
		class Base
		{
		private:
			bool exists_;

			SystemString fileDir_, fileName_;
			structure::Map< uint, structure::Element > data_;
		protected:
			void setFileName(SystemString const& name) { fileName_ = name; }
			structure::Map< uint, structure::Element >& getData() { return data_; }
			structure::Map< uint, structure::Element > const& getData() const { return data_; }

			void checkExists();

			virtual void load();

			virtual RPG2kString getHeader() const = 0;
			virtual SystemString defaultName() const { return ""; }

			SystemString getFileName() const { return (fileDir_ + PATH_SEPR + fileName_); }
			SystemString const& getDirectory() const { return fileDir_; }

			structure::Map< uint, structure::Descriptor >& getDescriptor() const;

			Base(SystemString const& dir);
			Base(SystemString const& dir, SystemString const& name);

			Base(Base const& src);
			Base& operator =(Base const& src);
		public:
			virtual ~Base();

			bool exists() const { return exists_; }

			void reset();

			virtual void save();

			structure::Element& operator [](uint index);
			structure::Element const& operator [](uint index) const;
		}; // class Base

		class DefineLoader
		{
		private:
			structure::Map< RPG2kString, structure::Map< uint, structure::Descriptor > > defineBuff_;
			std::map< RPG2kString, RPG2kString > defineText_;
			std::set< RPG2kString > isArray_;
		protected:
			void parse(structure::Map< uint, structure::Descriptor >& res, std::list< RPG2kString > const& token);

			structure::Map< uint, structure::Descriptor >& load(RPG2kString const& name);

			DefineLoader();
			DefineLoader(DefineLoader const& dl);

			~DefineLoader();
		public:
			static DefineLoader& instance();

			structure::Map< uint, structure::Descriptor >& get(RPG2kString const& name);
			structure::ArrayDefine getArrayDefine(RPG2kString const& name);

			bool isArray(RPG2kString const& typeName) const
			{
				return isArray_.find(typeName) != isArray_.end();
			}

			static void toToken(std::list< RPG2kString >& token, std::istream& stream);
		}; // class DefineLoader
	} // namespace model
} // namespace rpg2kLib

#endif
