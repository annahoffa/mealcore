import React from 'react';
import ToolbarAddNewProduct from '../toolbar-add-new-product/toolbar-add-new-product.component';
import ToolbarManageNote from '../toolbar-manage-note/toolbar-manage-note.component';
import ToolbarManageAllergySymptoms from '../toolbar-manage-allergy-symptoms/toolbar-manage-allergy-symptoms.component';
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