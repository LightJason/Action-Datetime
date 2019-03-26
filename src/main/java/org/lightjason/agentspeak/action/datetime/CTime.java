/*
 * @cond LICENSE
 * ######################################################################################
 * # LGPL License                                                                       #
 * #                                                                                    #
 * # This file is part of the LightJason AgentSpeak(L++)                                #
 * # Copyright (c) 2015-19, LightJason (info@lightjason.org)                            #
 * # This program is free software: you can redistribute it and/or modify               #
 * # it under the terms of the GNU Lesser General Public License as                     #
 * # published by the Free Software Foundation, either version 3 of the                 #
 * # License, or (at your option) any later version.                                    #
 * #                                                                                    #
 * # This program is distributed in the hope that it will be useful,                    #
 * # but WITHOUT ANY WARRANTY; without even the implied warranty of                     #
 * # MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                      #
 * # GNU Lesser General Public License for more details.                                #
 * #                                                                                    #
 * # You should have received a copy of the GNU Lesser General Public License           #
 * # along with this program. If not, see http://www.gnu.org/licenses/                  #
 * ######################################################################################
 * @endcond
 */

package org.lightjason.agentspeak.action.datetime;

import org.lightjason.agentspeak.common.IPath;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ITerm;

import javax.annotation.Nonnull;
import java.time.ZonedDateTime;
import java.util.List;


/**
 * action for getting the current time.
 * The action returns the time elements
 * of a parsed string or date-time object,
 * if the string is empty or "now" the current
 * date-time is used
 *
 * {@code [Hour|Minute|Second|Nano] = .datetime/time( "now" );}
 */
public final class CTime extends IDateTime
{
    /**
     * serial id
     */
    private static final long serialVersionUID = -5507360709996185053L;
    /**
     * action name
     */
    private static final IPath NAME = namebyclass( CTime.class, "datetime" );

    @Nonnull
    @Override
    public IPath name()
    {
        return NAME;
    }

    @Override
    protected boolean elements( @Nonnull final ZonedDateTime p_datetime, @Nonnull final List<ITerm> p_return )
    {
        p_return.add( CRawTerm.of( p_datetime.getHour() ) );
        p_return.add( CRawTerm.of( p_datetime.getMinute() ) );
        p_return.add( CRawTerm.of( p_datetime.getSecond() ) );
        p_return.add( CRawTerm.of( p_datetime.getNano() ) );
        return true;
    }

}
