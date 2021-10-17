import React from 'react';
import { Link } from 'react-router-dom';

import { Grid, Typography } from '@material-ui/core';
import AllergenWarning from '../allergen-warning/allergen-warning.component';

import './response-items-grid.styles.scss';


const ItemsGrid = ({ items }) => {

  const getGridItems = (items) => (
    items.map((item) => (
        <Grid item xs={12} id={item.id}>
          <Link className={`grid-link ${item.allergenWarning ? 'grid-link-with-warning' : ''}`} to={{ pathname: `/productinfo/${item.id}` }}>
            <div className='grid-item'>
              <div className='image-container'>
                <img src={item.images.find(obj => obj.url)?.url ?? ''} alt='Product' />
              </div>
              <Typography variant='h6'>
                {item.name}
              </Typography>
            </div>
            <div className='warning-icon'>
              {item.allergenWarning ? <AllergenWarning /> : null}
            </div>
          </Link>
        </Grid>
      ),
    )
  );

  return (
    <div className='grid-placeholder'>
      <Grid container spacing={4}>
        {getGridItems(items)}
      </Grid>
    </div>
  );
};

export default ItemsGrid;
