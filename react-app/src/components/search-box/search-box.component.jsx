import React from 'react';

import { InputBase } from '@material-ui/core';
import SearchIcon from '@material-ui/icons/Search';

import './search-box.styles.scss';


const SearchBox = (props) => {
  return (
    <div className='search'>
      <div className='icon-container'>
        <SearchIcon />
      </div>
      <InputBase
        type='search'
        className='search-input'
        placeholder='Wyszukaj...'
        onKeyDown={props.onKeyDown}
        inputProps={{ 'aria-label': 'search' }}
      />
    </div>
  );
};

export default SearchBox;
