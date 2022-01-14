import React, { useEffect, useState } from 'react';
import { makeStyles, Typography } from '@material-ui/core';
import DashboardElementsColumn from './dashboard-elements-column/dashboard-elements-column.component';
import DashboardProduct from './dashboard-product/dashboard-product.component';
import ReactionRatingBar from './reaction-rating-bar';
import apiCall from '../utils/apiCall';
import { Skeleton } from '@material-ui/lab';


const useStyles = makeStyles(() => ({
  dishCategory: {
    margin: '15px 0;',
  },
  // TODO: add restraints to the parent container so that the reactions can be on the right side
  // titleContainer: {
  //   display: 'flex',
  //   justifyContent: 'space-between',
  //   alignItems: 'center',
  // },
  titleContainer: {
    display: 'flex',
    alignItems: 'center',
    '&>*': {
      marginRight: '20px',
    },
  },
}));

// TODO: api refactor + component atomization
const CategorizeProducts = ({ userProducts, date }) => {
  const classes = useStyles();
  const DISH_CATEGORIES = { 'BREAKFAST': [], 'LUNCH': [], 'DINNER': [], 'SUPPER': [], 'SNACK': [], 'OTHER': [] };

  const assignProductToCategory = (product) => {
    switch(product.category) {
      case 'BREAKFAST':
        DISH_CATEGORIES['BREAKFAST'].push(product);
        break;
      case 'LUNCH':
        DISH_CATEGORIES['LUNCH'].push(product);
        break;
      case 'DINNER':
        DISH_CATEGORIES['DINNER'].push(product);
        break;
      case 'SUPPER':
        DISH_CATEGORIES['SUPPER'].push(product);
        break;
      case 'SNACK':
        DISH_CATEGORIES['SNACK'].push(product);
        break;
      case 'OTHER':
        DISH_CATEGORIES['OTHER'].push(product);
        break;
      default:
        DISH_CATEGORIES['OTHER'].push(product);
    }
  };

  // TODO: refactor
  userProducts.forEach(item => assignProductToCategory(item));

  // const { isLoading, isError, data } = useQuery('reactions', getUserReactions);
  // const userReactions = data;
  // console.log('reactions');
  // console.log(data);
  // if(isLoading || isError) return (<div>loading</div>);

  const [userReactions, setUserReactions] = useState(undefined);
  useEffect(() => {
    apiCall(`/api/user/getReactions?date=${date}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    }).then(data => setUserReactions(data))
    .catch(error => console.log(error));
  }, [date]);

  //fixme :c
  return (
    (userReactions === undefined) ? <Skeleton /> : (
      <div>
        {DISH_CATEGORIES['BREAKFAST'].length === 0 ? null :
          <div className={classes.dishCategory}>
            <div className={classes.titleContainer}>
              <Typography variant='h6'>Śniadanie</Typography>
              <ReactionRatingBar category='BREAKFAST'
                savedReaction={userReactions.find(item => item.category === 'BREAKFAST')?.value} />
            </div>
            <DashboardElementsColumn items={DISH_CATEGORIES['BREAKFAST']} component={DashboardProduct} date={date} />
          </div>
        }

        {DISH_CATEGORIES['LUNCH'].length === 0 ? null :
          <div className={classes.dishCategory}>
            <div className={classes.titleContainer}>
              <Typography variant='h6'>Lunch</Typography>
              <ReactionRatingBar category='LUNCH'
                savedReaction={userReactions.find(item => item.category === 'LUNCH')?.value} />
            </div>
            <DashboardElementsColumn items={DISH_CATEGORIES['LUNCH']} component={DashboardProduct} date={date} />
          </div>
        }

        {DISH_CATEGORIES['DINNER'].length === 0 ? null :
          <div className={classes.dishCategory}>
            <div className={classes.titleContainer}>
              <Typography variant='h6'>Obiad</Typography>
              <ReactionRatingBar category='DINNER'
                savedReaction={userReactions.find(item => item.category === 'DINNER')?.value} />
            </div>
            <DashboardElementsColumn items={DISH_CATEGORIES['DINNER']} component={DashboardProduct} date={date} />
          </div>
        }

        {DISH_CATEGORIES['SUPPER'].length === 0 ? null :
          <div className={classes.dishCategory}>
            <div className={classes.titleContainer}>
              <Typography variant='h6'>Kolacja</Typography>
              <ReactionRatingBar category='SUPPER'
                savedReaction={userReactions.find(item => item.category === 'SUPPER')?.value} />
            </div>
            <DashboardElementsColumn items={DISH_CATEGORIES['SUPPER']} component={DashboardProduct} date={date} />
          </div>
        }

        {DISH_CATEGORIES['SNACK'].length === 0 ? null :
          <div className={classes.dishCategory}>
            <div className={classes.titleContainer}>
              <Typography variant='h6'>Przekąska</Typography>
              <ReactionRatingBar category='SNACK'
                savedReaction={userReactions.find(item => item.category === 'SNACK')?.value} />
            </div>
            <DashboardElementsColumn items={DISH_CATEGORIES['SNACK']} component={DashboardProduct} date={date} />
          </div>
        }

        {DISH_CATEGORIES['OTHER'].length === 0 ? null :
          <div className={classes.dishCategory}>
            <div className={classes.titleContainer}>
              <Typography variant='h6'>Inne</Typography>
              <ReactionRatingBar category='OTHER'
                savedReaction={userReactions.find(item => item.category === 'OTHER')?.value} />
            </div>
            <DashboardElementsColumn items={DISH_CATEGORIES['OTHER']} component={DashboardProduct} date={date} />
          </div>
        }
      </div>)
  );
};

export default CategorizeProducts;