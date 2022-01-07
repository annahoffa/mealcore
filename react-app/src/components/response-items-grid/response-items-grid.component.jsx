import React from 'react';
import { Link } from 'react-router-dom';

import { Grid, Typography } from '@material-ui/core';
import AllergenWarning from '../allergen-warning/allergen-warning.component';

import './response-items-grid.styles.scss';


const ItemsGrid = ({ items, onSelect }) => {

  const getGridItems = (items) => (
    items.map((item) => {
      if (onSelect) {
        return (
          <Grid item xs={12} id={item.id} onClick={()=>{onSelect(item.id)}}>
            {/*TODO: Adjust for products which have both allergen warnings and reaction warnings*/}
            <div
              className={`grid-link ${item.allergenWarning ? 'grid-link-with-warning' : ''} ${!item.allergenWarning && item.badReaction ? 'grid-link-with-bad-reaction' : ''}`}>
              <div className='grid-item'>
                <div className='image-container'>
                  <img src={item.images.find(obj => obj.url)?.url ?? ''} alt='Product' />
                </div>
                <Typography variant='body1'>
                  {item.name}
                </Typography>
              </div>
              <div className='warning-icon'>
                {item.allergenWarning ? <AllergenWarning /> : null}
              </div>
            </div>
          </Grid>
        );
      }
        return (
          <Grid item xs={12} id={item.id}>
            {/*TODO: Adjust for products which have both allergen warnings and reaction warnings*/}
            <Link
              className={`grid-link ${item.allergenWarning ? 'grid-link-with-warning' : ''} ${!item.allergenWarning && item.badReaction ? 'grid-link-with-bad-reaction' : ''}`}
              to={{ pathname: `/productinfo/${item.id}` }}>
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
        );
      },
    )
  );

  return (
    <div className='grid-placeholder'>
      <Grid container justifyContent="center" alignItems="center" spacing={6}>
        {getGridItems(items)}
      </Grid>
    </div>
  );
};

export default ItemsGrid;
