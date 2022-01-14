import React from 'react';
import { Typography } from '@material-ui/core';
import FeatureCard from '../../components/feature-card/feature-card.component';
import mainPicture from '../../assets/picture-salad.png';

import './homepage.styles.scss';


const Homepage = () => {

  return (
    <main>
      <section className='section-item'>
        <img className='background-image' src={mainPicture} alt='Salad' />
        <Typography className='overline' variant='overline'>Zdrowe jedzenie</Typography>
        <span className='content'>Skuteczne diety</span>
      </section>
      <section className='section' style={{ height: '40rem', backgroundColor: '#F3F3F4' }}>
        <Typography variant='h4'>Dlaczego warto wybrać nas?</Typography>
        <div className='section-content'>
          <FeatureCard text={'Skorzystaj z rozbudowanej bazy produktów z całej polski'} title={'Baza produktów'}
            imagePath={'https://images.pexels.com/photos/1092730/pexels-photo-1092730.jpeg?auto=compress&cs=tinysrgb&dpr=3&h=750&w=1260'} />
          <FeatureCard text={'Dbaj o zdrowie monitorując dzienne spożycie kalorii oraz wartości odżywczych'}
            title={'Zdrowie'}
            imagePath={'https://images.pexels.com/photos/4021775/pexels-photo-4021775.jpeg?cs=srgb&dl=pexels-karolina-grabowska-4021775.jpg&fm=jpg'} />
          <FeatureCard text={'Popraw swoje samopoczucie dzięki spersonalizowanym ostrzeżeniom'} title={'Personalizacja'}
            imagePath={'https://images.pexels.com/photos/1028599/pexels-photo-1028599.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940'} />
        </div>
      </section>
      <section className='section' style={{ height: '30rem', backgroundColor: '#E4E3E3' }}>
        <Typography variant='h3'>Główne zalety</Typography>
        <Typography variant='subtitle1' className='section-content' style={{ width: '60%', minWidth: '30rem' }}>
          <p>Jednym z kluczowych czynników wpływającym na nasze zdrowie jest sposób odżywiania się. Celem aplikacji
            MealCore jest uporządkowanie informacji związanych ze spożywanymi posiłkami oraz pomoc w rozpoznawaniu,
            diagnozie oraz pracy z alergiami i nietolerancjami pokarmowymi. Dzięki dostępie do rozbudowanej bazy
            produktów w łatwy sposób można monitorować swoje cele oraz zdobyć szczegółowe informacja na temat jedzonych
            produktów.
            <br />
            Zaawansowany algorytm na bieżąco wykrywa produkty problematyczne oraz wyświetla spersonalizowane ostrzeżenia
            dopasowane do użytkownika. </p>
        </Typography>
      </section>
    </main>
  );
};

export default Homepage;
