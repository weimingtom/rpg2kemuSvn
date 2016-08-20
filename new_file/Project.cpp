#include "Project.hpp"
#include "Debug.hpp"


namespace rpg2kLib
{
	namespace model
	{
		Project::Project(SystemString baseDir)
		: baseDir_(baseDir)
		, ldb_(baseDir), lmt_(baseDir)
		{
			init();
		}

		void Project::init()
		{
		// LcfSaveData
			lastSaveDataStamp_ = 0;
			lastSaveDataID_ = ID_MIN;

			for(uint i = ID_MIN; i <= SAVE_DATA_MAX; i++) {
				lsd_.add(i, baseDir_, i);

				if( lsd_[i].exists() ) {
					std::vector< uint64_t > stamp = lsd_[i][100].getArray1D()[1].getBinary();
					uint64_t cur = stamp.at(0);
					if(cur > lastSaveDataStamp_) {
						lastSaveDataID_ = i;
						lastSaveDataStamp_ = cur;
					}

					cout
						<< "TimeStamp id: " << std::setw(2) << i << ", "
						<< std::hex << std::setw(16) << cur << ", "
						<< std::dec << std::setw(16) << cur << ", ";
				}
			}
		// set LcfSaveData buffer
			lsd_.add(ID_MIN-1);
		}

		Project::~Project()
		{
		}

		uint Project::getCurrentMapID() const
		{
			return getLSD().eventState(EV_ID_PARTY).mapID();
		}

		MapUnit & Project::getLMU(uint id)
		{
			if( !lmu_.exists(id) ) lmu_.add(id, baseDir_, id);
			return lmu_[id];
		}
		SaveData& Project::getLSD(uint id)
		{
			if( (ID_MIN <= id) && (id <= SAVE_DATA_MAX) ) return lsd_[id];
			else throw std::out_of_range("Invalid LSD ID");
		}

		bool Project::canTeleport() const
		{
			return getLSD()[101].getArray1D().exists(121)
				? getLSD()[101].getArray1D()[121]
				: getLMT().canTeleport( getCurrentMapID() );
		}
		bool Project::canEscape() const
		{
			return getLSD()[101].getArray1D().exists(122)
				? getLSD()[101].getArray1D()[122]
				: getLMT().canEscape( getCurrentMapID() );
		}
		bool Project::canSave() const
		{
			return getLSD()[101].getArray1D().exists(123)
				? getLSD()[101].getArray1D()[123]
				: getLMT().canSave( getCurrentMapID() );
		}
		bool Project::canOpenMenu() const
		{
			return getLSD()[101].getArray1D().exists(124)
				? getLSD()[101].getArray1D()[124]
				: true;
		}

		void Project::loadLSD(uint id)
		{
			if( (id < ID_MIN) || ( SAVE_DATA_MAX < id) ) throw std::out_of_range("Invalid LSD ID.");
			else getLSD() = lsd_[id];
		}

		void Project::saveLSD(uint id)
		{
			if( (id < ID_MIN) || ( SAVE_DATA_MAX < id) ) throw std::out_of_range("Invalid LSD ID.");

			SaveData& lsd = getLSD();
		// set preview
			structure::Array1D& prev = lsd[100];
			prev.clear();
			std::vector< uint16_t >& mem = lsd.member();
			// set time stamp
			lastSaveDataStamp_ += 1000;
			prev[1] = std::vector< uint64_t >(1, lastSaveDataStamp_);
			// set front character status
			if( mem.size() ) {
				int frontCharID = mem[0];
				prev[11] = name(frontCharID);
				prev[12] = level(frontCharID);
				prev[13] = hp(frontCharID);
			}
			// set faceSet
			for(uint i = 0; i < mem.size(); i++) {
				prev[21 + 2*i] = faceSet(mem[i]);
				prev[22 + 2*i] = faceSetPos(mem[i]);
			}

			lsd_[id] = lsd;
			lsd_[id].save();
		}

		bool Project::isBelow(uint chipID)
		{
			std::vector< uint8_t > const& lower = ldb_.lowerChipFlag(chipSetID());
			std::vector< uint8_t > const& upper = ldb_.upperChipFlag(chipSetID());

			int index = 0;

			if( (0 <= chipID) && (chipID < 3000) ) index =  0 + chipID/1000;
			else if(chipID == 3028) index =  3 + 0;
			else if(chipID == 3078) index =  3 + 1;
			else if(chipID == 3128) index =  3 + 2;
			else if( ( 4000 <= chipID) && (chipID <  5000) ) {
				index = 6 + (chipID-4000)/50;
				if( (lower[index] & 0x30) == 0x30 ) {
					switch( (chipID-4000) % 50 ) {
						case 0x14: case 0x15: case 0x16: case 0x17:
						case 0x21: case 0x22: case 0x23: case 0x24: case 0x25:
						case 0x2a: case 0x2b:
						case 0x2d: case 0x2e:
							return false;
						default:
							return true;
					}
				}
			}
			else if( ( 5000 <= chipID) && (chipID <  5144) ) index = 18 + chipID-5000;
			else if( (10000 <= chipID) && (chipID < 10144) )
				return (upper[chipID-10000] & 0x30) == 0x00;
			else throw std::invalid_argument("Invalid chipSetID.");

			return (lower[index] & 0x30) == 0x00;
		}
		bool Project::isAbove(uint chipID)
		{
			std::vector< uint8_t > const& lower = ldb_.lowerChipFlag(chipSetID());
			std::vector< uint8_t > const& upper = ldb_.upperChipFlag(chipSetID());

			int index = 0;

			if( (0 <= chipID) && (chipID < 3000) ) index =  0 + chipID/1000;
			else if(chipID == 3028) index =  3 + 0;
			else if(chipID == 3078) index =  3 + 1;
			else if(chipID == 3128) index =  3 + 2;
			else if( ( 4000 <= chipID) && (chipID <  5000) ) {
				index = 6 + (chipID-4000)/50;
				if( (lower[index] & 0x30) == 0x30 ) {
					switch((chipID-4000)%50) {
						case 0x14: case 0x15: case 0x16: case 0x17:
						case 0x21: case 0x22: case 0x23: case 0x24: case 0x25:
						case 0x2a: case 0x2b:
						case 0x2d: case 0x2e:
							return true;
						default:
							return false;
					}
				}
			}
			else if( ( 5000 <= chipID) && (chipID <  5144) ) index = 18 + getLSD().getReplace(CHIP_LOWER, chipID-5000);
			else if( (10000 <= chipID) && (chipID < 10144) ) return ( upper[getLSD().getReplace(CHIP_UPPER, chipID-10000)] & 0x30 ) == 0x10;
			else throw std::invalid_argument("Invalid chipSetID.");

			return (lower.at(index) & 0x30) == 0x10;
		}
		bool Project::isCounter(uint chipID)
		{
			std::vector< uint8_t > const& upper = ldb_.upperChipFlag( chipSetID() );

			if((10000 <= chipID) && (chipID < 10144))
				return (upper[chipID-10000] & 0x40) != 0x00;
			else return false;
		}
		uint8_t Project::getPass(uint chipID)
		{
			std::vector< uint8_t > const& lower = ldb_.lowerChipFlag(chipSetID());
			std::vector< uint8_t > const& upper = ldb_.upperChipFlag(chipSetID());

			int index = 0;

			if((0 <= chipID) && (chipID < 3000)) index = 0 + chipID/1000;
			else if(chipID == 3028) index = 3 + 0;
			else if(chipID == 3078) index = 3 + 1;
			else if(chipID == 3128) index = 3 + 2;
			else if( ( 4000 <= chipID) && (chipID <  5000) ) index =  6 + (chipID-4000)/50;
			else if( ( 5000 <= chipID) && (chipID <  5144) ) index = 18 +  getLSD().getReplace(CHIP_LOWER, chipID-5000);
			else if( (10000 <= chipID) && (chipID < 10144) ) return upper[getLSD().getReplace(CHIP_UPPER, chipID-10000)];
			else throw std::invalid_argument("Invalid chipSetID.");

			return lower.at(index);
		}
		int Project::getTerrainID(uint chipID)
		{
			std::vector< uint16_t > const& data = ldb_.terrain( chipSetID() );

			int index = 0;

			if((0 <= chipID) && (chipID < 3000)) index = 0 + chipID/1000;
			else if(chipID == 3028) index = 3 + 0;
			else if(chipID == 3078) index = 3 + 1;
			else if(chipID == 3128) index = 3 + 2;
			else if( (4000 <= chipID) && (chipID < 5000) ) index =  6 + (chipID-4000)/50;
			else if( (5000 <= chipID) && (chipID < 5144) ) index = 18 + getLSD().getReplace(CHIP_LOWER, chipID-5000);
			else throw std::invalid_argument("Invalid chipSetID."); // + chipSetID;

			return data.at(index);
		}

		int Project::currentPageID(uint eventID)
		{
			structure::Array1D* res = currentPage( getLMU()[81].getArray2D()[eventID][5] );
			return (res == NULL) ? INVALID_PAGE_ID : res->getIndex();
		}
		structure::Array1D* Project::currentPage(structure::Array2D const& pages) const
		{
			for(structure::Array2D::ReverseIterator it = pages.rbegin(); it != pages.rend(); ++it) {
				if(
					it.second().exists() &&
					getLSD().validPageMap( it.second()[2] )
				) return &it.second();
			}

			return NULL;
		}

		bool Project::equip(uint charID, uint itemID)
		{
			DataBase& ldb = getLDB();
			SaveData& lsd = getLSD();
			int itemNum = lsd.getItemNum(itemID);

			if(itemNum == 0) return false;

			uint type = ldb[13].getArray2D()[itemID][3];
			if( (type < ITEM_WEAPON) || (ITEM_ACCESSORY < type) ) {
				throw std::logic_error("Not a equipable item.");
			} else type--;

			std::vector< uint16_t > vec = lsd.character()[charID][61].getBinary();
			vec[type] = itemID;
			lsd.character()[charID][61].getBinary() = vec;

			lsd.setItemNum(itemID, --itemNum); // decrement item stock

			return true;
		}
		void Project::unequip(uint charID, EquipType type)
		{
			SaveData& lsd = getLSD();

			std::vector< uint16_t > vec = lsd.character()[charID][61].getBinary();

			lsd.setItemNum( vec[type], lsd.getItemNum(vec[type]+1) );
			vec[type] = 0;

			lsd.character()[charID][61].getBinary() = vec;
		}

		RPG2kString const& Project::systemGraphic() const
		{
			structure::Array1D& sys = getLSD()[101];

			if( sys.exists(21) ) return sys[21];
			else return getLDB()[22].getArray1D()[19];
		}
		WallpaperType Project::wallpaperType() const
		{
			structure::Array1D& sys = getLSD()[101];

			if( sys.exists(22) ) return (WallpaperType)sys[22].get_int();
			else return (WallpaperType)getLDB()[22].getArray1D()[71].get_int();
		}
		font::Face::Type Project::fontType() const
		{
			structure::Array1D& sys = getLSD()[101];

			if( sys.exists(23) ) return (font::Face::Type)sys[23].get_int();
			else return (font::Face::Type)getLDB()[22].getArray1D()[72].get_int();
		}

		void Project::newGame()
		{
			SaveData& lsd = getLSD();
			DataBase& ldb = getLDB();
		// clear the save data buffer
			lsd.reset();
		// set party, boat, ship and airShip start point
			structure::Array1D const& startLMT = getLMT().getStartPoint();
			structure::Array1D const& sysLDB = getLDB()[22];
			for(uint i = 1; i <= (EV_ID_AIRSHIP-EV_ID_PARTY); i++) {
				structure::EventState& dst = lsd.eventState(i + EV_ID_PARTY);

				dst[11] = startLMT[10*i + 1].get_int();
				dst[12] = startLMT[10*i + 2].get_int();
				dst[13] = startLMT[10*i + 3].get_int();
				dst[21] = (int)EV_DIR_DOWN;
				dst[22] = (int)EV_DIR_DOWN;
				dst[73] = sysLDB[10+i].get_string();
				if( sysLDB[10+i + 3].exists() ) dst[74] = sysLDB[10+i + 3].get_int();
			}
		// set start member
			lsd.member() = sysLDB[22].getBinary();
		// set party's char graphic
			if( lsd.memberNum() ) {
				structure::Array1D const& frontChar = getLDB().character()[ lsd.member()[0] ];
				structure::EventState& party = lsd.eventState(EV_ID_PARTY);
				party[21] = EV_DIR_DOWN;
				party[22] = EV_DIR_DOWN;
				party[73] = frontChar[3].get_string();
				party[74] = frontChar[4].get_int();
			}
		// move to start point
			move(startLMT[1], startLMT[2], startLMT[3]);
		// set character paramaeter
			structure::Array2D const& charsLDB = ldb[11];
			structure::Array2D& charsLSD = lsd[108];
			for(structure::Array2D::Iterator it = charsLDB.begin(); it != charsLDB.end(); ++it) {
				if( !it.second().exists() ) continue;

				structure::Array1D const& charLDB = it.second();
				structure::Array1D& charLSD = charsLSD[ it.first() ];

				int level = charLDB[7].get_int();
				charLSD[31] = level; // level
				charLSD[32] = 0; // charLDB[]; // experience

				charLSD[61] = charLDB[51].getBinary(); // equip

				charLSD[33] = 0;
				charLSD[34] = 0;
				charLSD[71] = ldb.getBasicStatus( it.first(), level, PRM_HP ); // current HP
				charLSD[72] = ldb.getBasicStatus( it.first(), level, PRM_MP ); // current MP
			}
		}

		void Project::move(uint mapID, int x, int y)
		{
			SaveData& lsd = getLSD();
		// set party position
			structure::EventState& party = lsd.eventState(EV_ID_PARTY);
			party[11] = mapID; party[12] = x; party[13] = y;

			/*
			 * the new party position must be set before this
			 * because getLMU() returns the current party mapID's LMU
			 */
			MapUnit& lmu = getLMU();
		// clear changed values
			lsd[111].getArray1D().clear();
		// reset chip replace
			lsd.resetReplace();
		// set map event position
			structure::Array2D const& mapEvent = lmu[81];
			structure::Array2D& states = lsd.eventState();
			for(structure::Array2D::Iterator it = mapEvent.begin(); it != mapEvent.end(); ++it) {
				if( !it.second().exists() ) continue;

				const structure::Array1D* page = currentPage( it.second()[5] );
				if(page == NULL) continue;

				structure::Array1D const& src = *page;
				structure::Array1D& dst = states[ it.first() ];
				dst[11] = 0; // mapID
				dst[12] = it.second()[2].get_int(); // x
				dst[13] = it.second()[3].get_int(); // y
				dst[73] = src[21].get_string(); // charSet
				dst[74] = src[22].get_int(); // charSetPos
				dst[75] = src[23].get_int(); // charSetPat
			}
		}

		RPG2kString Project::panorama()
		{
			if( getLSD()[111].getArray1D().exists(32) ) return getLSD()[111].getArray1D()[32];
			else if( getLMU()[31].get_bool() ) return getLMU()[32];
			else return RPG2kString();
		}

		int Project::chipSetID()
		{
			return getLSD()[111].getArray1D().exists(5)
				? getLSD()[111].getArray1D()[5]
				: getLMU()[1];
		}

		RPG2kString Project::name(uint charID) const
		{
			return getLSD().character().exists(charID, 1)
				? getLSD().character()[charID][1]
				: getLDB().character()[charID][1];
		}
		RPG2kString Project::title(uint charID) const
		{
			return getLSD().character().exists(charID, 2)
				? getLSD().character()[charID][2]
				: getLDB().character()[charID][2];
		}
		RPG2kString Project::charSet(uint charID) const
		{
			return getLSD().character().exists(charID, 11)
				? getLSD().character()[charID][11]
				: getLDB().character()[charID][3];
		}
		int Project::charSetPos(uint charID) const
		{
			return getLSD().character().exists(charID, 12)
				? getLSD().character()[charID][12]
				: getLDB().character()[charID][4];
		}
		RPG2kString Project::faceSet(uint charID) const
		{
			return getLSD().character().exists(charID, 21)
				? getLSD().character()[charID][21]
				: getLDB().character()[charID][15];
		}
		int Project::faceSetPos(uint charID) const
		{
			return getLSD().character().exists(charID, 22)
				? getLSD().character()[charID][22]
				: getLDB().character()[charID][16];
		}

		int Project::level(uint charID) const
		{
			return getLSD().character().exists(charID, 31)
				? getLSD().character()[charID][31]
				: getLDB().character()[charID][7];
		}
		int Project::conditionID(uint charID) const
		{
			return CONDITION_NORMAL;
/*
			return getLSD().character().exists(charID, )
				? getLSD().character()[charID][]
				: getLDB().character()[charID][];
 */
		}
		RPG2kString Project::condition(uint charID) const
		{
			int id = conditionID(charID);

			if( id == CONDITION_NORMAL ) return getLDB()[21].getArray1D()[126];
			else return getLDB()[18].getArray2D()[id][1];
		}
		int Project::conditionColor(uint charID) const
		{
			int id = conditionID(charID);

			if( id == CONDITION_NORMAL ) return font::FNT_NORMAL;
			else return getLDB()[18].getArray2D()[id][3];
		}
		int Project::hp(uint charID) const
		{
			return getLSD().character()[charID][71];
		}
		int Project::mp(uint charID) const
		{
			return getLSD().character()[charID][72];
		}
		int Project::param(uint charID, ParamType t) const
		{
			structure::Array1D const& curChar = getLSD().character()[charID];
			switch(t) {
				case PRM_HP: case PRM_MP:
					return getLDB().getBasicStatus(charID, curChar[31], t) + curChar[33 + t].get_int();
				case PRM_ATTACK: case PRM_GAURD: case PRM_MIND: case PRM_SPEED:
					return getLDB().getBasicStatus(charID, curChar[31], t) + curChar[41 + t - PRM_ATTACK].get_int();
				default: return 0;
			}
		}
		int Project::exp(uint charID) const
		{
			return getLSD().character()[charID][32];
		}
		int Project::nextLevelExp(uint charID) const
		{
			return 0;
/*
			if( getLSD().character().exists(charID, 22) ) return getLSD().character()[charID][22];
			else return getLDB().character()[charID][16];
 */
		}
		bool Project::checkLevel(uint charID)
		{
			return false;
		}
		void Project::processAction(uint eventID, Action::Type act, std::vector< int > const& arg)
		{
			switch(act) {
				case Action::Move::UP:
					break;
				case Action::Move::RIGHT:
					break;
				case Action::Move::DOWN:
					break;
				case Action::Move::LEFT:
					break;
				case Action::Move::RIGHT_UP:
					break;
				case Action::Move::RIGHT_DOWN:
					break;
				case Action::Move::LEFT_DOWN:
					break;
				case Action::Move::LEFT_UP:
					break;
				case Action::Move::RANDOM:
					break;
				case Action::Move::TO_PARTY:
					break;
				case Action::Move::FROM_PARTY:
					break;
				case Action::Move::A_STEP:
					break;
				case Action::Face::UP:
					break;
				case Action::Face::RIGHT:
					break;
				case Action::Face::DOWN:
					break;
				case Action::Face::LEFT:
					break;
				case Action::Turn::RIGHT_90:
					break;
				case Action::Turn::LEFT_90:
					break;
				case Action::Turn::OPPOSITE:
					break;
				case Action::Turn::RIGHT_OR_LEFT_90:
					break;
				case Action::Turn::RANDOM:
					break;
				case Action::Turn::TO_PARTY:
					break;
				case Action::Turn::OPPOSITE_OF_PARTY:
					break;
				case Action::HALT:
					break;
				case Action::BEGIN_JUMP:
					break;
				case Action::END_JUMP:
					break;
				case Action::FIX_DIR:
					break;
				case Action::UNFIX_DIR:
					break;
				case Action::SPEED_UP:
					break;
				case Action::SPEED_DOWN:
					break;
				case Action::FREQ_UP:
					break;
				case Action::FREQ_DOWN:
					break;
				case Action::SWITCH_ON:
					break;
				case Action::SWITCH_OFF:
					break;
				case Action::CHANGE_GRAPHIC:
					break;
				case Action::PLAY_SOUND:
					break;
				case Action::BEGIN_SLIP:
					break;
				case Action::END_SLIP:
					break;
				case Action::BEGIN_ANIME:
					break;
				case Action::END_ANIME:
					break;
				case Action::TRANS_UP:
					break;
				case Action::TRANS_DOWN:
					break;
				default: throw("Unknown action.");
			}
		}
	} // namespace model
} // namespace rpg2kLib
