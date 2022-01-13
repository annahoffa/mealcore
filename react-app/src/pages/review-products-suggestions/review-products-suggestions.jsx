import React, { useEffect, useState } from 'react';
import apiCall from '../../utils/apiCall';
import CircularProgress from '@mui/material/CircularProgress';
import MainContent from '../../components/main-content/main-content.component';
import ItemsGrid from '../../components/response-items-grid/response-items-grid.component';


const ReviewProductsSuggestions = () => {
  const [userSuggestions, setState] = useState(undefined);

  useEffect(() => {
    apiCall('/api/products/getUserSuggestions')
    .then(data => setState(data))
    .catch(error => console.log(error));
  }, []);

  return (
    <MainContent>
      {userSuggestions === undefined ? <CircularProgress color="success" size='5rem'/> : <ItemsGrid items={userSuggestions} />}
    </MainContent>
  );
};

export default ReviewProductsSuggestions;