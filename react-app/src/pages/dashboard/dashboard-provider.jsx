import React, { createContext, useContext, useState } from 'react';
import apiCall from '../../utils/apiCall';
import { format } from 'date-fns';
import { AuthContext } from '../../appContext/providers';
import { useMutation, useQuery } from 'react-query';


let defaultState = {};

export const DashboardProviderContext = createContext(defaultState);


const DashboardProvider = (props) => {
    const authContext = useContext(AuthContext);
    const [date, setDate] = useState(format(new Date(), 'yyyy-MM-dd'));

    const nutritionalRequirementsQuery = useQuery(['nutritionalRequirements', date],
      (context) =>
        apiCall(`/api/user/getNutritionalRequirements?date=${context.queryKey[1]}`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },
        }),
      { enabled: authContext.isLoggedIn },
    );

    const userProductsQuery = useQuery(['userProducts', date],
      (context) => {
        return apiCall(`/api/user/getUserProducts?date=${context.queryKey[1]}`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },
        });
      },
      { enabled: authContext.isLoggedIn });

    const userExercisesQuery = useQuery(['userExercises', date],
      (context) =>
        apiCall(`/api/user/getUserExercises?date=${context.queryKey[1]}`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },
        }),
      { enabled: authContext.isLoggedIn });


    const mutation = async({ product, page = 0 }) => {
      return await apiCall(`/api/products/suggestions?query=${product}&page=${page}`);
    };

    const searchProductMutation = useMutation(mutation);

    return (
      <DashboardProviderContext.Provider value={{
        date,
        setDate,
        searchProductMutation,
        nutritionalRequirementsQuery,
        userProductsQuery,
        userExercisesQuery,
      }}>
        {props.children}
      </DashboardProviderContext.Provider>
    );
  }
;

export default DashboardProvider;
