import React from 'react';
import AccountCircleIcon from '@material-ui/icons/AccountCircle';


export const HEADER_DATA_AUTHENTICATED = [
  {
    label: 'Panel użytkownika',
    href: '/dashboard',
  },
  {
    label: 'Kalkulator BMI',
    href: '/bmicalculator',
  },
  {
    label: 'Wyszukiwarka produktów',
    href: '/productsearch',
  },
  // {
  //   label: 'Cennik',
  //   href: '/pricing',
  // },
  {
    icon: <AccountCircleIcon />,
    label: 'Konto',
    href: '',
  },
];

export const DRAWER_DATA_AUTHENTICATED = [
  {
    label: 'Panel użytkownika',
    href: '/dashboard',
  },
  {
    label: 'Kalkulator BMI',
    href: '/bmicalculator',
  },
  {
    label: 'Wyszukiwarka produktów',
    href: '/productsearch',
  },
  {
    label: 'Cennik',
    href: '/pricing',
  },
  {
    label: 'Ustawienia konta',
    href: '/myaccount',
  },
  {
    label: 'Wyloguj',
    href: '/logout',
  },
];
