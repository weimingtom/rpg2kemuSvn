#ifndef _INC__RPG2K__MAP_HPP
#define _INC__RPG2K__MAP_HPP

#include "Define.hpp"

#include <map>
#include <stdexcept>


namespace rpg2kLib
{
	namespace structure
	{
		template< typename Key, typename T >
		class Map
		{
		private:
			std::multimap< Key, T* > data_;
			typedef typename std::multimap< Key, T* >::value_type val_type;
			typedef typename std::multimap< Key, T* >::const_iterator iterator;
			typedef typename std::multimap< Key, T* >::const_reverse_iterator reverse_iterator;
		public:
			class Iterator
			{
				friend class Map< Key, T >;
			private:
				Map< Key, T > const& owner_;
				iterator it_;
			protected:
				Iterator();
				Iterator(Map< Key, T > const& owner, iterator it)
				: owner_(owner), it_(it)
				{
				}
			public:
				bool operator !=(Iterator const& it) const { return this->it_ != it.it_; }
				bool operator ==(Iterator const& it) const { return this->it_ == it.it_; }

				Key const& first() const { return it_->first; }
				T& second() { return *(it_->second); }

				Iterator& operator ++() { ++it_; return *this; }
				Iterator& operator --() { --it_; return *this; }
			};
			class ReverseIterator
			{
				friend class Map< Key, T >;
			private:
				Map< Key, T > const& owner_;
				reverse_iterator it_;
			protected:
				ReverseIterator();
				ReverseIterator(Map< Key, T > const& owner, reverse_iterator it)
				: owner_(owner), it_(it)
				{
				}
			public:
				bool operator !=(ReverseIterator const& it) const { return this->it_ != it.it_; }
				bool operator ==(ReverseIterator const& it) const { return this->it_ == it.it_; }

				Key const& first() const { return it_->first; }
				T& second() { return *(it_->second); }

				ReverseIterator& operator ++() { ++it_; return *this; }
				ReverseIterator& operator --() { --it_; return *this; }
			};

			Map() : data_() {}
			Map(Map< Key, T > const& src) : data_()
			{
				for(iterator it = src.data_.begin(); it != src.data_.end(); ++it)
					data_.insert( val_type( it->first, new T( *(it->second) ) ) );
			}

			Map< Key, T >& operator =(Map< Key, T > const& src)
			{
				clear();

				for(iterator it = src.data_.begin(); it != src.data_.end(); ++it)
					data_.insert( val_type( it->first, new T( *(it->second) ) ) );

				return *this;
			}

			void clear()
			{
				for(iterator it = data_.begin(); it != data_.end(); ++it) delete it->second;
				data_.clear();
			}

			virtual ~Map() { clear(); }

			uint size() const { return data_.size(); }

			bool exists(Key const& key) const { return data_.find(key) != data_.end(); }

			T& get(Key const& key) const
			{
				iterator it = data_.find(key);
				if( it != data_.end() ) return *(it->second);
				else throw std::invalid_argument("Key doesn't exist in Map.");
			}
			T& operator [](Key const& key) const { return *( data_.find(key)->second ); }

			// skips remove if doesn't exist
			void remove(Key const& key)
			{
				if( exists(key) ) {
					for(iterator it = data_.find(key); it->first == key; ++it) delete it->second;
					data_.erase(key);
				}
			}

			bool empty() const { return data_.empty(); }

			void addReference(Key const& key, T& inst)
			{
				data_.insert( val_type(key, &inst) );
			}

			void add(Key const& key) { data_.insert( val_type( key, new T() ) ); }
			template< typename Arg1 >
			void add(Key const& key, Arg1& a1)
			{
				data_.insert( val_type( key, new T(a1) ) );
			}
			template< typename Arg1, typename Arg2 >
			void add(Key const& key, Arg1& a1, Arg2& a2)
			{
				data_.insert( val_type( key, new T(a1, a2) ) );
			}
			template< typename Arg1, typename Arg2, typename Arg3 >
			void add(Key const& key, Arg1& a1, Arg2& a2, Arg3& a3)
			{
				data_.insert( val_type( key, new T(a1, a2, a3) ) );
			}
			template< typename Arg1, typename Arg2, typename Arg3, typename Arg4 >
			void add(Key const& key, Arg1& a1, Arg2& a2, Arg3& a3, Arg4& a4)
			{
				data_.insert( val_type( key, new T(a1, a2, a3, a4) ) );
			}

			Iterator begin() const { return Iterator( *this, data_.begin() ); }
			Iterator end  () const { return Iterator( *this, data_.end  () ); }

			ReverseIterator rbegin() const { return ReverseIterator( *this, data_.rbegin() ); }
			ReverseIterator rend  () const { return ReverseIterator( *this, data_.rend  () ); }
		}; // class Map

		class Descriptor;
		typedef Map< uint, Descriptor > const& ArrayDefine;
	} // namespace structure
} // namespace rpg2kLib

#endif // _INC__RPG2K__MAP_HPP
