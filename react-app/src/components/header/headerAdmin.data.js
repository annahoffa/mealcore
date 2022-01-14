import React from 'react';
import AccountCircleIcon from '@material-ui/icons/AccountCircle';


export const HEADER_DATA_ADMIN = [
  {
    label: 'Wyszukiwarka produktów',
    href: '/productsearch',
  },
  {
    label: 'Dodaj produkt',
    href: '/suggestproduct',
  },
  {
    label: 'Oczekujące produkty',
    href: '/waitlist',
  },
  {
    icon: <AccountCircleIcon />,
    label: 'Konto',
    href: '',
  },
];

export const DRAWER_DATA_ADMIN = [
  {
    label: 'Wyszukiwarka produktów',
    href: '/productsearch',
  },
  {
    label: 'Dodaj produkt',
    href: '/suggestproduct',
  },
  {
    label: 'Oczekujące produkty',
    href: '/waitlist',
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
