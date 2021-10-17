import React from 'react';
import FacebookIcon from '@material-ui/icons/Facebook';
import InstagramIcon from '@material-ui/icons/Instagram';
import TwitterIcon from '@material-ui/icons/Twitter';


export const FOOTER_DATA = [
  {
    id: 1,
    title: 'Zasoby',
    items: [
      {
        id: 1,
        label: 'Umowa użytkownika',
        href: '/terms',
      },
      {
        id: 2,
        label: 'Polityka Prywatności',
        href: '/privacypolicy',
      },
      {
        id: 3,
        label: 'Cennik',
        href: '/',
      },
      {
        id: 4,
        label: 'Zarządzanie plikami cookies',
        href: '/',
      },
    ],
  },
  {
    id: 2,
    title: 'Odkrywaj',
    items: [
      {
        id: 1,
        label: 'O nas',
        href: '/',
      },
      {
        id: 2,
        label: 'Zespół',
        href: '/',
      },
    ],
  },
  {
    id: 3,
    title: 'Kontakt',
    items: [
      {
        id: 1,
        label: '+48 XXX XXX XXX',
        href: '/',
      },
      {
        id: 2,
        label: 'MealCore',
        href: '/',
      },
    ],
  },
];


export const SOCIAL_MEDIA_DATA = [
  {
    id: 1,
    icon: <FacebookIcon />,
    label: 'Facebook',
    href: '/',
  },
  {
    id: 2,
    icon: <InstagramIcon />,
    label: 'Instagram',
    href: '/',
  },
  {
    id: 3,
    icon: <TwitterIcon />,
    label: 'Twitter',
    href: '/',
  },
];
