import React, { useContext, useEffect, useState } from 'react';
import { AuthContext } from '../../appContext/providers';
import apiCall from '../../utils/apiCall';
import MainContent from '../../components/main-content/main-content.component';
import ItemsGrid from '../../components/response-items-grid/response-items-grid.component';
import { Typography } from '@material-ui/core';
import Loader from '../../components/loader/loader';


const ProblematicProductsSummaryPage = () => {
  const authContext = useContext(AuthContext);
  const [productsSummary, setProductsSummary] = useState(null);

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

  return (
    <MainContent>
      <Typography variant='h6'>
        To produkty, które pojawiały się w Twoich listach gdy czułeś/aś się źle.<br />Ich spożycie może przyczynić się
        do zwiększenia objawów alergicznych.
      </Typography>
      {productsSummary ? <ItemsGrid items={productsSummary.products} /> : <Loader width='35px' height='35px' />}
    </MainContent>
  );
};

export default ProblematicProductsSummaryPage;