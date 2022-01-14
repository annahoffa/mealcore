import React, { useState } from 'react';
import apiCall from '../../utils/apiCall';
import MainContent from '../../components/main-content/main-content.component';
import ItemsGrid from '../../components/response-items-grid/response-items-grid.component';
import { useQuery } from 'react-query';
import { Pagination } from '@material-ui/lab';
import { CircularProgress } from '@material-ui/core';


const SearchResultsPage = (props) => {
  const userQuery = props.match.params.query; // query extracted from the browser's url field
  const [pageNumber, setPageNumber] = useState(1);

  const productsQuery = useQuery(['productsQuery', userQuery, pageNumber],
    (context) => {
      return apiCall(`/api/products/suggestions?query=${context.queryKey[1]}&page=${context.queryKey[2] - 1}`);
    });

  return (
    <MainContent>
      <h1>Wyniki wyszukiwania:</h1>
      {productsQuery.isSuccess ? <><ItemsGrid items={productsQuery.data.products} />
          <Pagination
            count={Math.ceil(productsQuery.data.productCount / 20)} defaultPage={1} page={pageNumber}
            onChange={(event, page) => setPageNumber(page)} /></> :
        <CircularProgress color='success' size='5rem' />}
    </MainContent>
  );
};

export default SearchResultsPage;
