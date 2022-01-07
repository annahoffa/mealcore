import React, { useEffect, useState } from 'react';
import apiCall from '../../utils/apiCall';
import { Skeleton } from '@material-ui/lab';
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
      {userSuggestions === undefined ? <Skeleton /> : <ItemsGrid items={userSuggestions} />}
    </MainContent>
  );
};

export default ReviewProductsSuggestions;