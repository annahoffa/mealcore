import React from 'react';

import { Typography } from '@material-ui/core';

import './feature-card.styles.scss';
//import { CARD_DATA as cardData } from './feature-card.data'; //TODO: Replace placeholder data with an API call


const FeatureCard = ({ imagePath, text }) => (
  <div className='feature-card'>
    <div className='relative-content-wrapper'>
      <div className='image-container'>
        <img src='https://images.pexels.com/photos/1028599/pexels-photo-1028599.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940' alt='Decoration'/>
        { /*<img className='image' src={`${imagePath}`} />*/}
      </div>
      <div className='card-surface'>
        <div className='text-container'>
          <Typography className='text-title' variant='subtitle2' component='p'>Zdjęcie</Typography>
          <Typography className='text-body' variant='body2' component='p'>Skuteczne i smaczne posiłki.</Typography>
          {/*<Typography variant='body2' component='p'>{text}</Typography>*/}
        </div>
      </div>
    </div>
  </div>
);

export default FeatureCard;
