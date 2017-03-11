import React, { Component } from 'react';
import Block from './Block';


class Row extends Component {

  render () {

    return (
      <div>
      {
        this.props.rowData.map((row) => {
          return (
            <Block />
          )
        })
      }
      </div>
    )

  }
}


export default Row;
