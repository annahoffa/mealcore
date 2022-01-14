import React from 'react';
import { Link } from 'react-router-dom';
import { Button } from '@material-ui/core';
import { FOOTER_DATA as footerData, SOCIAL_MEDIA_DATA as socialMediaData } from './footer.data';

import './footer.styles.scss';


const Column = ({ title, items }) => {

  const hrefs = items.map((item) => (
    <Button {...{
      key: item.id,
      to: item.href,
      component: Link,
      style: { color: '#E3E3E4' },
    }}>
      {item.label}
    </Button>
  ));

  return (
    <div className='column'>
      <h3>{title}</h3>
      {hrefs}
    </div>
  );
};

const Footer = () => {

  const displaySocialMediaLinks = () => {
    const socialMediaLinks = socialMediaData.map(({ id, icon, label, href }) => (
      <Button {...{
        key: id,
        to: href,
        component: Link,
        style: { color: '#E3E3E4' },
      }}>
        {icon}&nbsp;{label}
      </Button>
    ));

    return (
      <div className='column'>
        <h2>MealCore</h2>
        {socialMediaLinks}
      </div>
    );
  };

  const displaySiteLinks = () => {
    return footerData.map(({ id, title, items }) => (
      <Column key={id} title={title} items={items} />
    ));
  };

  return (
    <footer className='footer'>
      <div className='footer-left-container'>
        {displaySocialMediaLinks()}
      </div>
      <div className='footer-right-container'>
        {displaySiteLinks()}
      </div>
    </footer>
  );
};

export default Footer;
