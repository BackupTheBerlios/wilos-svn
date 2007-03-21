/*
 * Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
 * Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <aubry@irit.fr>
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package wilos.presentation.assistant.ressources;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class ProfileReader 
{ 

  private Hashtable _sections; 

  /** 
   * Creates a new ProfileReader 
   */ 
  public ProfileReader() 
  { 
    _sections = new Hashtable(); 
  } 

  /** 
   * Load the current objet with the data found in the given stream 
   * @param aStream the stream that represent the INI file. 
   * @throws Exception in case of problems. 
   */ 
  public void load(InputStream aStream) throws Exception 
  { 
    if (null == aStream) 
    { 
      return; 
    } 

    BufferedReader reader = new BufferedReader(new InputStreamReader(aStream)); 
    String line = null; 
    String sectionName = null; 
    Hashtable section = null; 
    while ((line = reader.readLine()) != null) 
    { 
      line = line.trim(); 
      // All the data should be in a section 
      if (null == sectionName) 
      { 
        if ((!line.startsWith("[")) || (!line.endsWith("]"))) 
        { 
          throw new Exception("Invalid format: data found outside section"); 
        } 

        sectionName = line.substring(1, line.length() - 1).trim(); 
        addSection(sectionName); 
        section = getSection(sectionName); 
      } 
      else 
      { 
        if (line.startsWith("[")) 
        { 
          if (!line.endsWith("]")) 
          { 
            throw new Exception("Invalid format: no ending ] for section name"); 
          } 
          sectionName = line.substring(1, line.length() - 1).trim(); 
          addSection(sectionName); 
          section = getSection(sectionName); 
        } 
        else 
        { 
          addLineToSection(line, section); 
        } 
      } 
    } 
  } 

  /** 
   * Return the value of the given key in the given section 
   * @param aSectionName the name of the section 
   * @param aKey the key 
   * @return the value if found or null. 
   */ 
  public String getProperty(String aSectionName, String aKey) 
  { 
    Hashtable section = getSection(aSectionName); 
    if (null == section) 
    { 
      return null; 
    } 

    return (String) section.get(aKey); 
  } 

  private void addLineToSection(String aLine, Hashtable aSection) throws Exception 
  { 
    if (null == aLine) 
    { 
      return; 
    } 

    if (null == aSection) 
    { 
      throw new Exception("No section found to add data"); 
    } 

    aLine = aLine.trim(); 

    // lines that starts with ; are comments 
    if (aLine.startsWith(";")) 
    { 
      return; 
    } 

    // Avoid the empty lines 
    if (aLine.length() == 0) 
    { 
       return; 
    } 

    // The format of a line of data is: key = value 
    StringTokenizer st = new StringTokenizer(aLine, "="); 
    if (st.countTokens() != 2) 
    { 
      throw new Exception("Invalid format of data: " + aLine); 
    } 

    String key = st.nextToken().trim(); 
    // a key should not contain spaces 
    for (int index = 0; index < key.length(); index++) 
    { 
      if (Character.isWhitespace(key.charAt(index))) 
      { 
        throw new Exception("Invalid format of data: " + aLine); 
      } 
    } 
    
    String value = st.nextToken().trim(); 

    aSection.put(key, value); 
  } 

  private void addSection(String aSectionName) 
  { 
    if (null == aSectionName) 
    { 
      return; 
    } 

    Hashtable section = getSection(aSectionName); 
    if (null == section) 
    { 
      section = new Hashtable(); 
      _sections.put(aSectionName, section); 
    } 
  } 

  private Hashtable getSection(String aSectionName) 
  { 
    return (Hashtable) _sections.get(aSectionName); 
  } 


} 
