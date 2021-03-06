#ifndef _INC__RPG2K__MODEL__PROJECT__HPP
#define _INC__RPG2K__MODEL__PROJECT__HPP

#include "DataBase.hpp"
#include "MapTree.hpp"
#include "MapUnit.hpp"
#include "SaveData.hpp"

#include "SpecialArray1D.hpp"

#include "Font.hpp"


namespace rpg2kLib
{
	class Main;

	namespace model
	{
		class MapUnit;
		class SaveData;

		enum WallpaperType { ZOOM = 0, TILE, };

		class Project
		{
		private:
			SystemString baseDir_, rtpDir_;

			DataBase ldb_;
			MapTree  lmt_;
			structure::Map< uint, MapUnit  > lmu_;
			structure::Map< uint, SaveData > lsd_;

			uint lastSaveDataID_;
			uint64_t lastSaveDataStamp_;
		protected:
			void init();
			Project(Project const& src);
			Project& operator =(Project const& src);
		public:
			Project(SystemString baseDir=".");
			~Project();

			RPG2kString getGameTitle() const { return lmt_[0][1]; }

			SystemString const& getGameDir() const { return baseDir_; }

			uint getCurrentMapID() const;

			DataBase const& getLDB() const { return ldb_; }
			 MapTree const& getLMT() const { return lmt_; }
			SaveData const& getLSD() const { return lsd_[ID_MIN-1]; }

			structure::Array1D & getLMT(uint id) const { return lmt_[id]; }
			 MapUnit& getLMU(uint id);
			SaveData& getLSD(uint id);

			DataBase& getLDB() { return ldb_; }
			 MapTree& getLMT() { return lmt_; }
			 MapUnit& getLMU() { return getLMU( getCurrentMapID() ); }
			SaveData& getLSD() { return lsd_[ID_MIN-1]; }

			int lastLSD() const { return lastSaveDataID_; }
			uint64_t lastLSDStamp() const { return lastSaveDataStamp_; }

			int chipSetID();
			RPG2kString panorama();

			void newGame();

		// returns true if level up
			bool checkLevel(uint charID);

			void loadLSD(uint id);
			void saveLSD(uint id);

			bool canTeleport() const;
			bool canEscape  () const;
			bool canSave    () const;
			bool canOpenMenu() const;

			bool isAbove(uint chipID);
			bool isBelow(uint chipID);
			bool isCounter(uint chipID);
			uint8_t getPass(uint chipID);
			int getTerrainID(uint chipID);

			void processAction(uint eventID, Action::Type act, std::vector< int > const& arg);

			// returns true if equip success, false if failed
			bool   equip(uint charID, uint itemID);
			void unequip(uint charID, EquipType type);

			int currentPageID(uint eventID);
			structure::Array1D* currentPage(structure::Array2D const& pages) const;

			RPG2kString const& systemGraphic() const;
			WallpaperType wallpaperType() const;
			font::Face::Type fontType() const;

			void move(uint mapID, int x, int y);

			RPG2kString faceSet(uint charID) const;
			int faceSetPos(uint charID) const;
			RPG2kString charSet(uint charID) const;
			int charSetPos(uint charID) const;

			RPG2kString name(uint charID) const;
			RPG2kString title(uint charID) const;

			int level(uint charID) const;
			int levelExp(uint charID) const;
			int conditionID(uint charID) const;
			RPG2kString condition(uint charID) const;
			int conditionColor(uint charID) const;
			int hp(uint charID) const;
			int mp(uint charID) const;
			int param(uint charID, ParamType t) const;
			int exp(uint charID) const;
			int nextLevelExp(uint charID) const;
		}; // class Project
	} // namespace model
} // namespace rpg2kLib

#endif
