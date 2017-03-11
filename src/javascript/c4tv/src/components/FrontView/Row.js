import React, { Component } from 'react';
import Block from './Block';


class Row extends Component {

  render () {

    return (
      <div>
      {
        this.props.rowData.map((cell) => {
          return (
            <Block cellData={cell}/>
          )
        })
      }
      </div>
    )

  }
}


export default Row;
