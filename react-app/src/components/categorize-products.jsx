import React from 'react';
import { makeStyles, Typography } from '@material-ui/core';
import DashboardElementsColumn from './dashboard-elements-column/dashboard-elements-column.component';
import DashboardProduct from './dashboard-product/dashboard-product.component';


const useStyles = makeStyles(() => ({
  dishCategory: {
    margin: '15px 0;',
  },
}));

// TODO: api refactor + component atomization
const CategorizeProducts = ({ userProducts }) => {
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

  return (
    <>
      {DISH_CATEGORIES['BREAKFAST'].length === 0 ? null :
        <div className={classes.dishCategory}>
          <Typography variant='h6'>Śniadanie</Typography>
          <DashboardElementsColumn items={DISH_CATEGORIES['BREAKFAST']} component={DashboardProduct} />
        </div>
      }

      {DISH_CATEGORIES['LUNCH'].length === 0 ? null :
        <div className={classes.dishCategory}>
          <Typography variant='h6'>Lunch</Typography>
          <DashboardElementsColumn items={DISH_CATEGORIES['LUNCH']} component={DashboardProduct} />
        </div>
      }

      {DISH_CATEGORIES['DINNER'].length === 0 ? null :
        <div className={classes.dishCategory}>
          <Typography variant='h6'>Obiad</Typography>
          <DashboardElementsColumn items={DISH_CATEGORIES['DINNER']} component={DashboardProduct} />
        </div>
      }

      {DISH_CATEGORIES['SUPPER'].length === 0 ? null :
        <div className={classes.dishCategory}>
          <Typography variant='h6'>Kolacja</Typography>
          <DashboardElementsColumn items={DISH_CATEGORIES['SUPPER']} component={DashboardProduct} />
        </div>
      }

      {DISH_CATEGORIES['SNACK'].length === 0 ? null :
        <div className={classes.dishCategory}>
          <Typography variant='h6'>Przekąska</Typography>
          <DashboardElementsColumn items={DISH_CATEGORIES['SNACK']} component={DashboardProduct} />
        </div>
      }

      {DISH_CATEGORIES['OTHER'].length === 0 ? null :
        <div className={classes.dishCategory}>
          <Typography variant='h6'>Inne</Typography>
          <DashboardElementsColumn items={DISH_CATEGORIES['OTHER']} component={DashboardProduct} />
        </div>
      }
    </>
  );
};

export default CategorizeProducts;