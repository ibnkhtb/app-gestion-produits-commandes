import Carousel from 'react-bootstrap/Carousel';
import React, { useState } from 'react';
import './caroussel.css';

function Slider({ slides }) {
  const [index, setIndex] = useState(0);

  const handleSelect = (selectedIndex, e) => {
    console.log('selected index: ', selectedIndex)
    setIndex(selectedIndex);
  };

  return (
    <Carousel activeIndex={index}
              onSelect={handleSelect}
              nextIcon={<span aria-hidden="true" className="carousel-control-next-icon changed" />}
    >
      {slides.map((slide) => (
        <Carousel.Item key={slide.image} interval={slide.interval} className=''>
          <img
            className="d-block   carousel-img "
            src={slide.image}
            alt="First slide"
          />
        </Carousel.Item>
      ))}
    </Carousel>
  );
}

export default Slider;