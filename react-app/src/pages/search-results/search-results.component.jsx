import React, { useState, useEffect } from 'react';
import apiCall from '../../utils/apiCall';

import Skeleton from '@material-ui/lab/Skeleton';
import MainContent from '../../components/main-content/main-content.component';
import ItemsGrid from '../../components/response-items-grid/response-items-grid.component';


const SearchResultsPage = (props) => {
  const userQuery = props.match.params.query; // query extracted from the browser's url field
  const [state, setState] = useState();

  useEffect(() => {
    apiCall(`/api/products/suggestions/${userQuery}`)
    .then(data => setState(data))
    .catch(error => console.log(error));
  }, []);

  return (
    <MainContent>
      <h1>Wynik wyszukiwania:</h1>
      {state === undefined ? <Skeleton /> : <ItemsGrid items={state} />}
    </MainContent>
  );
};

export default SearchResultsPage;
