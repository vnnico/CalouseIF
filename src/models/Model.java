package models;


import services.ConnectionSingleton;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class Model {
	
	protected abstract String getTablename();
	protected abstract String getPrimarykey();
	
	public Model() {
		
	}
	
	/**
	 * GET All Values
	 * @param <FromModel>
	 * @param model
	 * @return
	 */
	protected <FromModel> ArrayList<FromModel> all(Class<FromModel> model){	
		try {
			
			// Initialize a list to store instances of FromModel
			ArrayList<FromModel> listToModels = new ArrayList<FromModel>();
			// Get the database connection singleton instance
			ConnectionSingleton db_connection = ConnectionSingleton.getInstance();
			// Prepare the SQL query to retrieve all rows from the specified table
			String query = "SELECT * FROM " + getTablename();
			 // Execute the query and get the result set
            PreparedStatement ps = db_connection.prepareStatement(query);
            ResultSet rs = db_connection.execQuery(ps);
            
            // Iterate through result set
            while (rs.next()) {
            	// Create a new instance of FromModel using reflection
                FromModel instance = model.getDeclaredConstructor().newInstance();
                // Declared fields of the instance's class
                Field[] fields = instance.getClass().getDeclaredFields();
                
                
        	    for (Field field : fields) {
        	    	// Skip fields named "tableName" and "primaryKey"
        	    	if(field.getName().equals("tableName") || field.getName().equals("primaryKey")) continue;
        	    	 // Make the field accessible for reflection
        	    	field.setAccessible(true);
        	    	 // Retrieve the field's value from the current row in the result set
    	            Object value = rs.getObject(field.getName());
    	         // Assign the retrieved value to the field in the instance
    	            field.set(instance, value);
        	    }
                listToModels.add(instance);
            }
            
            return listToModels;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		
		return null;
	}

	/**
	 * GET Latest Value
	 * @param <FromModel>
	 * @param model
	 * @return
	 */
	protected <FromModel> FromModel latest(Class<FromModel> model) {
	    try {
	    	// Descending order and limits the result to one row
	        String query = "SELECT * FROM " + getTablename() + " ORDER BY " + getPrimarykey() + " DESC LIMIT 1";
	    
	        // Get the database connection singleton instance
	        ConnectionSingleton db_connection = ConnectionSingleton.getInstance();
	        PreparedStatement ps = db_connection.prepareStatement(query);
	        ResultSet rs = ps.executeQuery();
	        
	        FromModel instance = model.getDeclaredConstructor().newInstance();
	        if (rs.next()) {
	        	
	            Field[] fields = instance.getClass().getDeclaredFields();
	            
	            for (Field field : fields) {
	            	
	                if (field.getName().equals("tableName") || field.getName().equals("primaryKey")) continue;
	                field.setAccessible(true);
	                Object value = rs.getObject(field.getName());
	                field.set(instance, value);
	            }
	        }
	        
	        return instance;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
	        e.printStackTrace(); 
	    }
	    
	    return null;
	}
	
	/**
	 * GET Value by a key
	 * @param <FromModel>
	 * @param model
	 * @param fromKey
	 * @return
	 */
	protected <FromModel> FromModel find(Class<FromModel> model, String fromKey){	
		try {
			ConnectionSingleton db_connection = ConnectionSingleton.getInstance();

			// Prepare a SQL query to select a record based on the primary key
			String query = "SELECT * FROM " + getTablename() + " WHERE " + this.getPrimarykey() + " = ?";
			
            PreparedStatement ps = db_connection.prepareStatement(query);
            ps.setString(1, fromKey);
            ResultSet rs = db_connection.execQuery(ps);
            
            FromModel instance = model.getDeclaredConstructor().newInstance();
            
            if (rs.next()) {
            	
                Field[] fields = instance.getClass().getDeclaredFields();
                
        	    for (Field field : fields) {
        	    	
        	    	if(field.getName().equals("tableName") || field.getName().equals("primaryKey")) continue;
        	        field.setAccessible(true);
    	            Object value = rs.getObject(field.getName());
    	            field.set(instance, value);
        	    }
            }
            
            return instance;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		
		return null;
	}
	
	
	/**
	 * POST value
	 * @param <FromModel>
	 * @param model
	 * @return
	 */
	protected <FromModel> FromModel insert(Class<FromModel> model){
		try {
			
			// Construct the list of column names and values for the SQL query
            StringBuilder columns = new StringBuilder();
            StringBuilder values = new StringBuilder();
            
            // Store the parameter values to be inserted into the database
            ArrayList<Object> parameters = new ArrayList<Object>();
            
            Field[] fields = this.getClass().getDeclaredFields();
            
            for (Field field : fields) {
            	
            	if(field.getName().equals("tableName") || field.getName().equals("primaryKey")) continue;
				field.setAccessible(true);
				
				// Retrieve the value of the field for the current object
				Object value = field.get(this);
				 // Append the column name and value placeholder to the query
				if(columns.length() > 0) {
					columns.append(", ");
					values.append(", ");
				}
				columns.append(field.getName());
				values.append("?");
				// Add the field's value to the parameters list (use null if the value is null)
				parameters.add(value == null ? null : value);
			}
            
            ConnectionSingleton db_connection = ConnectionSingleton.getInstance();
            
         // Construct the SQL INSERT query
			String query = "INSERT INTO " + getTablename() + " (" + columns + ") VALUES (" + values + ")";
            PreparedStatement ps = db_connection.prepareStatement(query);	
            
            // Set the values for the prepared statement using the parameters list
            for(int i = 0; i < parameters.size(); i++) {
            	if(parameters.get(i) == null) {
            		 // If the value is null, set the parameter to SQL NULL
            		ps.setNull(i + 1, java.sql.Types.VARCHAR);
            	}else {
            		// sset the parameter to the corresponding object value
            		ps.setObject(i + 1, parameters.get(i));            		
            	}
            }
            
            db_connection.execUpdate(ps);
            return (FromModel) this;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		
		return null;
	}
	
	
	/**
	 * PUT value
	 * @param <FromModel>
	 * @param model
	 * @param fromKey
	 * @return
	 */
	protected <FromModel> FromModel update(Class<FromModel> model, String fromKey) {
		try {
			// construct the SET clause of the SQL UPDATE query
            StringBuilder sets = new StringBuilder();
            ArrayList<Object> parameters = new ArrayList<Object>();
            
            Field[] fields = this.getClass().getDeclaredFields();
            
            // Ensure that the primary key is not null before proceeding
            if(this.getPrimarykey() == null) return null;
            
            for (Field field : fields) {
            	if(field.getName().equals("tableName") || field.getName().equals("primaryKey")) continue;
				field.setAccessible(true);
				Object value = field.get(this);
				// Skip the "id" field but add other fields to the SET clause
				if(!field.getName().equals("id")) {
					// Append the field name and value placeholder to the SET clause
					if(sets.length() > 0) {
						sets.append(", ");
					}
					sets.append(field.getName()).append(" = ?");
					parameters.add(value == null ? null : value);
				}				
			}
            
            // Add the primary key value as the last parameter for the WHERE clause
            parameters.add(fromKey);
            
			ConnectionSingleton db_connection = ConnectionSingleton.getInstance();
			// Construct the SQL UPDATE query
			String query = "UPDATE " + getTablename() + " SET " + sets + " WHERE " + getPrimarykey() + " = ?";
            PreparedStatement ps = db_connection.prepareStatement(query);	
            
            for(int i = 0; i < parameters.size(); i++) {
            	if(parameters.get(i) == null) {
            		ps.setNull(i + 1, java.sql.Types.VARCHAR);
            	}else {
            		ps.setObject(i + 1, parameters.get(i));            		
            	}
            }
            
            db_connection.execUpdate(ps);
            
            return (FromModel) this;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		
		return null;	
	}
	
	/**
	 * DELETE value
	 * @param <FromModel>
	 * @param model
	 * @param fromKey
	 * @return
	 */
	protected <FromModel> Boolean delete(Class<FromModel> model, String fromKey) {    
	    try {
	        String query = "DELETE FROM " + getTablename() + " WHERE " + this.getPrimarykey() + " = ?";
	        
	        ConnectionSingleton db_connection = ConnectionSingleton.getInstance();
	        PreparedStatement ps = db_connection.prepareStatement(query);
	        ps.setString(1, fromKey);
	        ps.executeUpdate();
	        
	        return true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
	        e.printStackTrace(); 
	    }
	    
	    return false;
	}

	/**
	 * ELOQUENT Has One
	 * @param <ToModel>
	 * @param otherModel
	 * @param Totablename
	 * @param fromKey
	 * @param toKey
	 * @return
	 */
	protected <ToModel> ToModel hasOne(Class<ToModel> otherModel, String Totablename,String fromKey, String toKey){
		try {
			
			// Construct the SQL query to find a related record in the target table
			String query = "SELECT * FROM " + Totablename + " WHERE " + Totablename + "." + toKey + " = ?;";
			
			ConnectionSingleton db_connection = ConnectionSingleton.getInstance();
            PreparedStatement ps = db_connection.prepareStatement(query);
            
            // Set the foreign key value as a parameter for the query
            ps.setString(1, fromKey);
            
            ResultSet rs = db_connection.execQuery(ps);
			
			if(rs.next()) {
				// Instance of the related model using reflection
				ToModel instance = otherModel.getDeclaredConstructor().newInstance();
                Field[] fields = instance.getClass().getDeclaredFields();
        	    for (Field field : fields) {
        	    	
        	    	if(field.getName().equals("tableName") || field.getName().equals("primaryKey")) continue;
        	        field.setAccessible(true);
        	        // Retrieve the value for the field from the current row in the result set
        	
    	            Object value = rs.getObject(field.getName());
    	            field.set(instance, value);
        	    }		
				
				return instance;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		
		return null;
	}
	
	/**
	 * ELOQUENT Has Many
	 * @param <ToModel>
	 * @param otherModel
	 * @param Totablename
	 * @param fromKey
	 * @param toKey
	 * @return
	 */
	protected  <ToModel> ArrayList<ToModel> hasMany(Class<ToModel> otherModel, String Totablename,String fromKey, String toKey){		
		try {
			ArrayList<ToModel> listToModels = new ArrayList<ToModel>();
			String query = "SELECT * FROM " + Totablename + " WHERE " + Totablename + "." + toKey + " = ?;";
			
			ConnectionSingleton db_connection = ConnectionSingleton.getInstance();
            PreparedStatement ps = db_connection.prepareStatement(query);
            ps.setString(1, fromKey);
            
            ResultSet rs = db_connection.execQuery(ps);
            
            while (rs.next()) {
            	
                ToModel instance = otherModel.getDeclaredConstructor().newInstance();
                Field[] fields = instance.getClass().getDeclaredFields();
                
        	    for (Field field : fields) {
        	    	
        	    	if(field.getName().equals("tableName") || field.getName().equals("primaryKey")) continue;
        	        
        	    	field.setAccessible(true);
    	            Object value = rs.getObject(field.getName());
    	            field.set(instance, value);
        	    }
                listToModels.add(instance);
            }
            
            return listToModels;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		
		return null;
	}
	
	
	/**
	 * WHERE IN CLAUSE
	 * @param <FromModel>
	 * @param model
	 * @param columnName
	 * @param ids
	 * @return
	 */
	protected <FromModel> ArrayList<FromModel> whereIn(Class<FromModel> model, String columnName, ArrayList<String> ids) {
	    try {
	    	
	    	// Construct placeholders for the IN clause
	    	StringBuilder queryIds = new StringBuilder();
	    	
	    	// Construct a list of placeholders (?, ?, ?) for the IN clause based on the number of IDs
	    	for(int i = 0; i < ids.size(); i++) {
	    		if(queryIds.length() > 0) {
	    			queryIds.append(", ");
	    		}
	    		queryIds.append("?");	    		
	    	}

	    	ConnectionSingleton db_connection = ConnectionSingleton.getInstance();
	    	
	    	// SQL query with the dynamically built IN clause
	        String query = "SELECT * FROM " + getTablename() + " WHERE " + columnName + " IN (" + queryIds + ")";
	        PreparedStatement ps = db_connection.prepareStatement(query);
	        
	        for(int i = 0; i < ids.size(); i++) {
	    		ps.setString(i + 1, ids.get(i));	    		
	    	}

	        ResultSet rs = db_connection.execQuery(ps);
	        
	        ArrayList<FromModel> listToModels = new ArrayList<>();
	        
	        while (rs.next()) {
	        	
	            FromModel instance = model.getDeclaredConstructor().newInstance();
	            
	            // Retrieve all declared fields of the instance's class
	            Field[] fields = instance.getClass().getDeclaredFields();
	            
	            for (Field field : fields) {
	                if (field.getName().equals("tableName") || field.getName().equals("primaryKey")) continue;
	                field.setAccessible(true);
	                Object value = rs.getObject(field.getName());
	                field.set(instance, value);
	            }
	            listToModels.add(instance);
	        }

	        return listToModels;
	    } catch (SQLException | ReflectiveOperationException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	/**
	 * WHERE CLAUSE
	 * @param <FromModel>
	 * @param model
	 * @param columnName
	 * @param operator
	 * @param key
	 * @return
	 */
	protected <FromModel> ArrayList<FromModel> where(Class<FromModel> model, String columnName, String operator, String key) {    
	    try {
	        ArrayList<FromModel> listToModels = new ArrayList<>();
	        String query = "SELECT * FROM " + getTablename() + " WHERE " + columnName + " " + operator + " ?";

	        
	        ConnectionSingleton db_connection = ConnectionSingleton.getInstance();
	        PreparedStatement ps = db_connection.prepareStatement(query);
	        ps.setString(1, key);
	        
	        
	        ResultSet rs = ps.executeQuery();
	        
	        while (rs.next()) {
	        	
	            FromModel instance = model.getDeclaredConstructor().newInstance();
	            Field[] fields = instance.getClass().getDeclaredFields();
	            
	            for (Field field : fields) {
	            	
	                if (field.getName().equals("tableName") || field.getName().equals("primaryKey")) continue;
	                field.setAccessible(true);
	                
	                Object value = rs.getObject(field.getName());
	                field.set(instance, value);
	            }
	            listToModels.add(instance);
	        }
	        
	        return listToModels;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
	        e.printStackTrace(); 
	    }
	    
	    return null;
	}

}
