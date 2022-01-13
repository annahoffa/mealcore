import React, { useState } from 'react';
import apiCall from '../../utils/apiCall';
import MainContent from '../../components/main-content/main-content.component';
import ItemsGrid from '../../components/response-items-grid/response-items-grid.component';
import { useQuery } from 'react-query';
import Loader from '../../components/loader/loader';
import { Pagination } from '@material-ui/lab';


const SearchResultsPage = (props) => {
  const userQuery = props.match.params.query; // query extracted from the browser's url field
  const [pageNumber, setPageNumber] = useState(0);

  const productsQuery = useQuery(['productsQuery', userQuery, pageNumber],
    (context) => {
      return apiCall(`/api/products/suggestions?query=${context.queryKey[1]}&page=${context.queryKey[2]}`);
    });

  return (
    <MainContent>
      <h1>Wyniki wyszukiwania:</h1>
      {productsQuery.isSuccess ? <><ItemsGrid items={productsQuery.data} /><Pagination count={1} defaultPage={0} page={pageNumber}
          onChange={(event, page) => setPageNumber(page)} /></> :
        <Loader width='35px' height='35px' />}
    </MainContent>
  );
};

export default SearchResultsPage;
