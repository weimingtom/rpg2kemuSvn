#if !RPG2K_IS_PSP
	#if RPG2K_USE_NEDMALLOC
		#include "nedmalloc.c"
		#define rpg2k_malloc nedalloc::nedmalloc
		#define rpg2k_free nedalloc::nedfree
	#elif RPG2K_USE_DLMALLOC
		#define USE_LOCKS 1
		#define USE_DL_PREFIX
		#include "malloc.c"
		#define rpg2k_malloc dlmalloc
		#define rpg2k_free dlfree
	#endif

	#include <cstddef>
	#include <new>

	void* operator new(std::size_t size) throw (std::bad_alloc)
	{
		return rpg2k_malloc(size);
	}
	void* operator new[](std::size_t size) throw (std::bad_alloc)
	{
		return rpg2k_malloc(size);
	}
	void operator delete(void* ptr) throw ()
	{
		rpg2k_free(ptr);
	}
	void operator delete[](void* ptr) throw ()
	{
		rpg2k_free(ptr);
	}
#endif
