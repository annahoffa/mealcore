import React from 'react';
import { useHistory } from 'react-router-dom';
import MainContent from '../../components/main-content/main-content.component';
import SearchBox from '../../components/search-box/search-box.component';
import { Typography } from '@material-ui/core';

import './product-search.styles.scss';


const ProductSearchPage = () => {
  let history = useHistory();
  let searchField = '';
  //let btnClick = false;

  const handleOnKeyDown = (e) => {
    // 'keypress' event misbehaves on mobile so instead we track the 'Enter'/code 13
    if(e.key === 'Enter') {
      searchField = e.target.value;
      history.push({ pathname: `/searchresults/${searchField}` });
    }
  };

  return (
    <MainContent>
      <Typography variant='h5' align='center'>Jakiego produktu aktualnie poszukujesz?</Typography>
      <SearchBox onKeyDown={handleOnKeyDown} />
    </MainContent>
  );
};

export default ProductSearchPage;
