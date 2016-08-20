#ifndef _INC__RPG2K__MODEL__EXECUTE_HPP
#define _INC__RPG2K__MODEL__EXECUTE_HPP

#include "Project.hpp"

#include <list>
#include <map>
#include <set>
#include <stack>


namespace rpg2kLib
{
	class Main;

	namespace structure
	{
		class Event;
		class Instruction;
	}

	namespace model
	{
		enum ExecuteState
		{
			EXE_SUCCESS = 0, EXE_FAILED, EXE_NOT_IMPLEMENTED,
			EXE_NEXT_FRAME
		};

		class Execute
		{
		private:
			Main& owner_;

			enum EventType { EV_COMMON, EV_MAP, EV_BATTLE, EV_TYPE_END };
			struct ExecInfo
			{
				EventType type;
				uint id;

				uint counter;
				bool end;

				EventStart start;
				uint page;
				structure::Event* event;
			};

			std::vector< std::map< uint, ExecInfo > > execInfo_;
			std::map< uint, uint > pageMap_;

			std::vector< std::map< uint, std::multimap< uint, uint > > >* eventMap_;
			std::map< uint, uint >* pageNo_;
			std::stack< ExecInfo, std::list< ExecInfo > > eventStack_;
		protected:
			Project& getProject();
			Main& getOwner() { return owner_; }

			static const int SKIP_CURRENT = -1;
			void skipToEndOfJunction(int nest = SKIP_CURRENT, int code = CODE__END_OF_JUNCTION);
			bool skipToElse(int nest = SKIP_CURRENT, int code = SKIP_CURRENT);

			bool nextEvent();

			bool isJunction(uint code) const;

			bool hasExec() const { return !eventStack_.empty(); }
			ExecInfo& curExec() { return eventStack_.top(); }
			ExecInfo const& curExec() const { return eventStack_.top(); }
			void pushExec(ExecInfo const&  exe) { eventStack_.push(exe); }
			void  popExec() { eventStack_.pop(); }

			static const uint CODE__END_OF_JUNCTION = 10;
		public:
			Execute(Main& m);

			const structure::Instruction* nextInst();
			const structure::Instruction* currentInst() const;

			void refresh(
				std::vector< std::map< uint, std::multimap< uint, uint > > >& eventMap,
				std::map< uint, uint >& pageNo
			);

			ExecuteState execute(structure::Instruction const& inst);
			ExecuteState executeBattle(structure::Instruction const& inst);
		private:
			class CommandInterface
			{
			private:
				Execute* owner_;
			protected:
				Execute& getOwner() const { return *owner_; }
			public:
				CommandInterface() {}
				CommandInterface(Execute& e) : owner_(&e) {}

				virtual ExecuteState operator()(
					RPG2kString const& strArg,
					std::vector< int32_t > const& args
				) const {
					throw std::runtime_error("Invalid event command.");
				};
			};

			std::map< uint, CommandInterface > commandTable_;
			std::map< uint, CommandInterface > battleCommandTable_;

			template< uint Code >
			class Command : public CommandInterface
			{
			public:
				Command() {}
				Command(Execute& e) : CommandInterface(e) {}
				virtual ExecuteState operator()(
					RPG2kString const& strArg,
					std::vector< int32_t > const& args
				) const;
				uint code() { return Code; }
			};
		}; // class Execute

		#if defined(RPG2K_ENABLE_EVENT_COMMAND)
			#define PP_codeProto(codeNo) \
				template< > \
				ExecuteState Execute::Command< codeNo >::operator()(RPG2kString const& strArg, std::vector< int32_t > const& args) const;

			PP_codeProto(    0); // end of event
			PP_codeProto(   10); // end of junction

			PP_codeProto(10110); // show message
			PP_codeProto(20110);
			PP_codeProto(10120); // set message config
			PP_codeProto(10130); // set message face set
			PP_codeProto(10140); // select junction
			PP_codeProto(20140);
			PP_codeProto(20141);
			PP_codeProto(10150); // input number

			PP_codeProto(10210); // change switch
			PP_codeProto(10220); // change variable
			PP_codeProto(10230); // set timer

			PP_codeProto(10310); // set money
			PP_codeProto(10320); // set item
			PP_codeProto(10330); // exchange member

			PP_codeProto(10410); // change exp value
			PP_codeProto(10420); // change level
			PP_codeProto(10430); // change character parameter
			PP_codeProto(10440); // add or remove skill
			PP_codeProto(10450); // change equipment
			PP_codeProto(10460); // change HP
			PP_codeProto(10470); // change MP
			PP_codeProto(10480); // set condition
			PP_codeProto(10490); // full cure
			PP_codeProto(10500); // damage character

			PP_codeProto(10610); // change character name
			PP_codeProto(10620); // change character title
			PP_codeProto(10630); // change character graphic
			PP_codeProto(10640); // change face graphic
			PP_codeProto(10650); // change transport graphic
			PP_codeProto(10660); // change system BGM
			PP_codeProto(10670); // change system SE
			PP_codeProto(10680); // change system graphic
			PP_codeProto(10690); // change screen refresh

			PP_codeProto(10710); // battle junction
			PP_codeProto(20710);
			PP_codeProto(20711);
			PP_codeProto(20712);
			PP_codeProto(20713);
			PP_codeProto(10720); // shop junction
			PP_codeProto(20720);
			PP_codeProto(20721);
			PP_codeProto(20722);
			PP_codeProto(10730); // hotel junction
			PP_codeProto(20730);
			PP_codeProto(20731);
			PP_codeProto(20732);
			PP_codeProto(10740); // input character name

			PP_codeProto(10810); // move to a place
			PP_codeProto(10820); // memorize current point
			PP_codeProto(10830); // move to the memorized point
			PP_codeProto(10840); // get on to the transport
			PP_codeProto(10850); // set transport posi
			PP_codeProto(10860); // set event position
			PP_codeProto(10870); // exchange event position

			PP_codeProto(10910); // get terrain id
			PP_codeProto(10920); // get event id

			PP_codeProto(11010); // erase screen
			PP_codeProto(11020); // show screen
			PP_codeProto(11030); // set screen color tone
			PP_codeProto(11040); // flash screen
			PP_codeProto(11050); // shake screen
			PP_codeProto(11060); // scroll screen
			PP_codeProto(11070); // set weather effect

			PP_codeProto(11110); // show picture
			PP_codeProto(11120); // move picture
			PP_codeProto(11130); // erase picture

			PP_codeProto(11210); // battle anime at field

			PP_codeProto(11310); // transparent party
			PP_codeProto(11320); // flash event
			PP_codeProto(11330); // set move
			PP_codeProto(11340); // process all moves
			PP_codeProto(11350); // dispose all moves

			PP_codeProto(11410); // wait

			PP_codeProto(11510); // play music
			PP_codeProto(11520); // fade out music
			PP_codeProto(11530); // memorize music
			PP_codeProto(11540); // play memorized music
			PP_codeProto(11550); // play sound
			PP_codeProto(11560); // play movie

			PP_codeProto(11610); // get key input

			PP_codeProto(11710); // change chip set
			PP_codeProto(11720); // change panorama
			PP_codeProto(11740); // change encount step
			PP_codeProto(11750); // replace chip

			PP_codeProto(11810); // add teleport link
			PP_codeProto(11820); // disable or enable teleport
			PP_codeProto(11830); // set escape link
			PP_codeProto(11840); // disable or enable escape

			PP_codeProto(11910); // call save screen
			PP_codeProto(11930); // disable or enable saving
			PP_codeProto(11950); // call menu
			PP_codeProto(11960); // disable or enable menu

			PP_codeProto(12010); // junction
			PP_codeProto(22010);
			PP_codeProto(22011);

			PP_codeProto(12110); // set label
			PP_codeProto(12120); // goto label

			PP_codeProto(12210); // loop
			PP_codeProto(22210);
			PP_codeProto(12220); // interrupt loop

			PP_codeProto(12310); // interrupt event
			PP_codeProto(12320); // erase event
			PP_codeProto(12330); // call event

			PP_codeProto(12410); // comment
			PP_codeProto(22410);
			PP_codeProto(12420); // gameover

			PP_codeProto(12510); // title

		// battle events
			PP_codeProto(13110); // change enemy HP
			PP_codeProto(13120); // change enemy MP
			PP_codeProto(13130); // change enemy condition
			PP_codeProto(13150); // appear enemy

			PP_codeProto(13210); // change backdrop
			PP_codeProto(13260); // show battle animation

			PP_codeProto(13310); // junction true  way
			PP_codeProto(23310); // junction false way
			PP_codeProto(23311); // end of junction

			PP_codeProto(13410); // interrupt battle

			#undef PP_codeProto
		#endif
	} // namespace model
} // namespace rpg2kLib

#endif
