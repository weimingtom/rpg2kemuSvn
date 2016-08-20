/* OTHER library */
#include <unistd.h>
#include <SDL.h>

#include <exception>
#include <new>

// My library
#include <rpg2kLib.hpp>

#if defined PSP
	#include <pspkernel.h>

	// PSP_MODULE_INFO("RPG2K_Emu_PSP", 0, 1, 1);
	PSP_MAIN_THREAD_ATTR(PSP_THREAD_ATTR_USER | THREAD_ATTR_VFPU);
#endif

#define EXP_CATCH 0

extern "C" int main(int argc, char** argv)
{
	#if EXP_CATCH
	bool excep = true;

	try {
	#endif

		rpg2kLib::debug::Logger::init();

		#if RPG2K_IS_PSP
			pspDebugScreenInit();
			rpg2kLib::debug::addAtExitFunction(sceKernelExitGame);
		#endif

	// get current directory
		static const size_t BUFF_SIZE  = 1024;
		char curDirBuff[BUFF_SIZE];
		const char* curDir = getcwd(curDirBuff, BUFF_SIZE);
		std::cout << "Current Directory: " << curDir << ";" << std::endl;
	// init
		rpg2kLib::Main m(curDir);
	// start
		std::cout << "Starting main routine." << std::endl;
		m.start();

	#if EXP_CATCH
		excep = false;
	} catch(exception e) {
		clog << e.what() << endl;
	} catch(string str) {
		clog << str << endl;
	} catch(const char* str) {
		clog << str << endl;
	} catch(...) {
		clog << "Catched unknown exception." << endl;
	}

	// exit
	if(excep) return EXIT_FAILURE;
	else return EXIT_SUCCESS;
	#else
	return EXIT_SUCCESS;
	#endif
}
