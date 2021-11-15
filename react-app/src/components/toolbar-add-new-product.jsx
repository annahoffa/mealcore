import React from 'react';
import { Link } from 'react-router-dom';
import IconButton from '@material-ui/core/IconButton';
import AddIcon from '@material-ui/icons/Add';
import { makeStyles, Tooltip } from '@material-ui/core';


const useStyles = makeStyles(() => ({
    icon: {
      maxWidth: 'max-content',
      maxHeight: 'fit-content',
      backgroundColor: '#F6F6F6',
      borderRadius: '5px',
    },
  }
));

const ToolbarAddNewProduct = () => {
  const { icon } = useStyles();

  return (
    <Tooltip title='Dodaj produkt' className={icon}>
      <IconButton to='/productsearch' component={Link}>
        <AddIcon />
      </IconButton>
    </Tooltip>
  );
};

export default ToolbarAddNewProduct;