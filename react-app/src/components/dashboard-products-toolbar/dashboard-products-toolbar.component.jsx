import React from 'react';
import ToolbarAddNewProduct from '../toolbar-add-new-product';
import ToolbarManageNote from '../toolbar-manage-note';
import ToolbarManageAllergySymptoms from '../toolbar-manage-allergy-symptoms';
import { makeStyles } from '@material-ui/core';


const useStyles = makeStyles(() => ({
  toolbar: {
    '&>*': {
      marginRight: '0.2em',
    },
  },
}));

const DashboardProductsToolbar = () => {
  const { toolbar } = useStyles();

  return (
    <div className={toolbar}>
      <ToolbarAddNewProduct />
      <ToolbarManageNote />
      <ToolbarManageAllergySymptoms />
    </div>
  );
};

export default DashboardProductsToolbar;