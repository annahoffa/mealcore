import React from 'react';

import './main-content.styles.scss';


const MainContent = ({ children }) => (
  <main>
    <div className='section-background'>
      <div className='section-foreground'>
        <div className='main-content-container'>
          {children}
        </div>
      </div>
    </div>
  </main>
);

export default MainContent;
