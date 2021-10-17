import React from 'react';

import {Typography} from '@material-ui/core';
import FeatureCard from '../../components/feature-card/feature-card.component';
import mainPicture from '../../assets/picture-salad.png';

import './homepage.styles.scss';


const Homepage = () => {

  return (
    <main>
      <section className='section-item'>
        <img className='background-image' src={mainPicture} alt='Salad'/>
        <Typography className='overline' variant='overline'>Zdrowe jedzenie</Typography>
        <span className='content'>Skuteczne diety.</span>
      </section>
      <section className='section' style={{ height: '30rem', backgroundColor: '#F3F3F4' }}>
        <Typography variant='h4'>Dlaczego warto wybrać nas?</Typography>
        <div className='section-content'>
          <FeatureCard />
          <FeatureCard />
          <FeatureCard />
        </div>
      </section>
      <section className='section' style={{ height: '30rem', backgroundColor: '#E4E3E3' }}>
        <Typography variant='h4'>Główne zalety</Typography>
        <div className='section-content'>
          <p>(Template text here)</p>
        </div>
      </section>
    </main>
  );
};

export default Homepage;
