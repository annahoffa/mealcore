import React from 'react';
import { Typography } from '@material-ui/core';

import './feature-card.styles.scss';
//import { CARD_DATA as cardData } from './feature-card.data';


const FeatureCard = ({ imagePath, text, title }) => (
  <div className='feature-card'>
    <div className='relative-content-wrapper'>
      <div className='image-container'>
        <img src={imagePath} alt='Decoration' />
      </div>
      <div className='card-surface'>
        <div className='text-container'>
          <Typography className='text-title' variant='subtitle2' component='p'>{title}</Typography>
          <Typography className='text-body' variant='body2' component='p'>{text}</Typography>
        </div>
      </div>
    </div>
  </div>
);

export default FeatureCard;
