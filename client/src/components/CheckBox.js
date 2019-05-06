import React from 'react';

const CheckBox = props => {
  return (
    <div className="form-group">
      <label for={props.name} className="form-label">
        
      </label>
      <div className="checkbox">
        
            <label key={props.option} className="checkbox-inline">
              <input
                id={props.name}
                name={props.name}
                onChange={props.handleChange}
                value={props.option}
                checked={props.selectedOptions.indexOf(props.option) > -1}
                type="checkbox"
              />
              {props.option}
            </label>        
      </div>
    </div>
  );
};

export default CheckBox;