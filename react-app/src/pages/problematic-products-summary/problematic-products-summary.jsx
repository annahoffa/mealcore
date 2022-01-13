import React, { useContext, useEffect, useState } from 'react';
import { AuthContext } from '../../appContext/providers';
import apiCall from '../../utils/apiCall';
import MainContent from '../../components/main-content/main-content.component';
import ItemsGrid from '../../components/response-items-grid/response-items-grid.component';
import CircularProgress from '@mui/material/CircularProgress';
import { Typography } from '@material-ui/core';


const ProblematicProductsSummaryPage = () => {
  const authContext = useContext(AuthContext);
  const [productsSummary, setProductsSummary] = useState();

  const getProblematicProducts = () => {
    if(authContext.isLoggedIn) {
      apiCall('/api/user/getProblematicProducts', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      })
      .then(data => setProductsSummary(data))
      .catch(error => console.log(error));
    }
  };

  useEffect(() => getProblematicProducts(), []);

  console.log(productsSummary);
  return (
    <MainContent>
      <Typography variant='h6'>
        To produkty, które pojawiały się w Twoich listach gdy czułeś/aś się źle.<br />Ich spożycie może przyczynić się
        do zwiększenia objawów alergicznych.
      </Typography>
      {productsSummary === undefined ? <CircularProgress color="success" size='5rem'/> : <ItemsGrid items={productsSummary.products} />}
    </MainContent>
  );
};

export default ProblematicProductsSummaryPage;