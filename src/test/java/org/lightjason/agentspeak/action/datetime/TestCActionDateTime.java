/*
 * @cond LICENSE
 * ######################################################################################
 * # LGPL License                                                                       #
 * #                                                                                    #
 * # This file is part of the LightJason                                                #
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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.error.context.CExecutionIllegealArgumentException;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ITerm;
import org.lightjason.agentspeak.language.execution.IContext;
import org.lightjason.agentspeak.testing.IBaseTest;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * test action datetime
 */
public final class TestCActionDateTime extends IBaseTest
{

    /**
     * data provider generator of apply-minus tests
     * @return data
     */
    public static Stream<Arguments> generateapplyminus()
    {
        return Stream.of(
            Arguments.of( new CApplyYears(), "2010-05-04T10:17:13Z[America/New_York]", 6, "2004-05-04T10:17:13Z[America/New_York]" ),
            Arguments.of( new CApplyMonths(), "2009-05-04T10:17:13Z[America/New_York]", 7, "2008-10-04T10:17:13Z[America/New_York]" ),
            Arguments.of( new CApplyDays(), "2008-05-04T10:17:13Z[America/New_York]", 5, "2008-04-29T10:17:13Z[America/New_York]" ),
            Arguments.of( new CApplyHours(), "2009-01-15T15:16:13Z[Europe/London]", 36, "2009-01-14T03:16:13Z[Europe/London]" ),
            Arguments.of( new CApplyMinutes(), "2007-01-15T10:23:13Z[Europe/London]", 187, "2007-01-15T07:16:13Z[Europe/London]" ),
            Arguments.of( new CApplySeconds(), "2006-01-15T10:23:13Z[Europe/London]", 4200, "2006-01-15T09:13:13Z[Europe/London]" ),
            Arguments.of( new CApplyNanoSeconds(), "2005-01-15T10:23:13Z[Europe/London]", 10200, "2005-01-15T10:23:12.999989800Z[Europe/London]" )
        );
    }


    /**
     * data provider generator of apply-plus tests
     * @return data
     */
    public static Stream<Arguments> generateapplyplus()
    {
        return Stream.of(
            Arguments.of( new CApplyYears(), "2010-05-04T10:17:13Z[America/New_York]", 12, "2022-05-04T10:17:13Z[America/New_York]" ),
            Arguments.of( new CApplyMonths(), "2009-05-04T10:17:13Z[America/New_York]", 8, "2010-01-04T06:17:13-05:00[America/New_York]" ),
            Arguments.of( new CApplyDays(), "2008-05-04T10:17:13Z[America/New_York]", 3, "2008-05-07T10:17:13Z[America/New_York]" ),
            Arguments.of( new CApplyHours(), "2009-01-15T10:16:13Z[Europe/London]", 120, "2009-01-20T10:16:13Z[Europe/London]" ),
            Arguments.of( new CApplyMinutes(), "2007-01-15T10:23:13Z[Europe/London]", 240, "2007-01-15T14:23:13Z[Europe/London]" ),
            Arguments.of( new CApplySeconds(), "2006-01-15T10:23:13Z[Europe/London]", 7205, "2006-01-15T12:23:18Z[Europe/London]" ),
            Arguments.of( new CApplyNanoSeconds(), "2005-01-15T10:23:13Z[Europe/London]", 15715, "2005-01-15T10:23:13.000015715Z[Europe/London]" )
        );
    }


    /**
     * data provider generator of between tests
     *
     * @return data
     */
    public static Stream<Arguments> generatebetween()
    {
        return Stream.of(
            Arguments.of( new CYearsBetween(), Stream.of(
                "2000-01-15T10:16:13Z[Europe/London]", "2000-01-15T10:16:13Z[Europe/London]",
                "2000-01-15T10:23:13Z[Europe/London]", "2020-05-04T10:17:13Z[America/New_York]"
            ), Stream.of( 0, 20 ) ),

            Arguments.of( new CMonthsBetween(), Stream.of(
                "1999-01-15T10:16:13Z[Europe/London]", "1999-01-15T10:16:13Z[Europe/London]",
                "1999-01-15T10:16:13Z[Europe/London]", "2001-01-15T10:16:13Z[Europe/London]"
            ), Stream.of( 0, 24 ) ),

            Arguments.of( new CDaysBetween(), Stream.of(
                "1998-01-15T10:23:13Z[Europe/London]", "1998-01-15T10:23:13Z[Europe/London]",
                "1998-06-15T10:23:13Z[Europe/London]", "1998-01-15T10:23:13Z[Europe/London]"
            ), Stream.of( 0, -151 ) ),

            Arguments.of( new CHoursBetween(), Stream.of(
                "1997-05-04T10:17:13Z[America/New_York]", "1997-05-04T10:17:13Z[America/New_York]",
                "1997-05-04T18:12:13Z[America/New_York]", "1997-05-04T10:17:13Z[America/New_York]"
            ), Stream.of( 0, -7 ) ),

            Arguments.of( new CMinutesBetween(), Stream.of(
                "1996-01-15T10:23:13Z[Europe/Paris]", "1996-01-15T10:23:13Z[Europe/Paris]",
                "1996-01-15T10:23:13Z[Europe/Paris]", "1996-01-15T16:23:13Z[Europe/Paris]"
            ), Stream.of( 0, 360 ) ),

            Arguments.of( new CSecondsBetween(), Stream.of(
                "1995-01-15T10:23:13Z[Europe/Madrid]", "1995-01-15T10:23:13Z[Europe/Madrid]",
                "1995-02-15T10:23:13Z[Europe/Madrid]", "1995-02-14T10:23:13Z[Europe/Madrid]"
            ), Stream.of( 0, -86400 ) )

        );
    }

    /**
     * test create error
     */
    @Test
    public void createerror()
    {
        Assertions.assertThrows( CExecutionIllegealArgumentException.class,
                                 () -> new CCreate().execute(
                                     false,
                                     IContext.EMPTYPLAN,
                                     Stream.of( "error" ).map( CRawTerm::of ).collect( Collectors.toList() ),
                                     Collections.emptyList()
                                 )
        );
    }

    /**
     * test create
     */
    @Test
    public void create()
    {
        final List<ITerm> l_return = new ArrayList<>();

        new CCreate().execute(
            false, IContext.EMPTYPLAN,
            Stream.of( "", "2007-12-03T10:15:30+01:00[Europe/Paris]", "now" ).map( CRawTerm::of ).collect( Collectors.toList() ),
            l_return
        );

        Assertions.assertEquals( 3, l_return.size() );
        Assertions.assertTrue( l_return.get( 0 ).raw() instanceof ZonedDateTime );
        Assertions.assertTrue( l_return.get( 1 ).raw() instanceof ZonedDateTime );
        Assertions.assertTrue( l_return.get( 2 ).raw() instanceof ZonedDateTime );

        Assertions.assertTrue( l_return.get( 0 ).<ZonedDateTime>raw().isBefore( l_return.get( 2 ).<ZonedDateTime>raw() ) );
    }


    /**
     * test build
     */
    @Test
    public void build()
    {
        final List<ITerm> l_return = new ArrayList<>();

        new CBuild().execute(
            false, IContext.EMPTYPLAN,
            Stream.of( 2013, 3, 13, 12, 11, 10, 9, "current", 2013, 3, 13, 12, 11, 10, 9, "Europe/Moscow" ).map( CRawTerm::of )
                  .collect( Collectors.toList() ),
            l_return
        );

        Assertions.assertEquals( 2, l_return.size() );
        Assertions.assertTrue( l_return.get( 0 ).raw() instanceof ZonedDateTime );
        Assertions.assertTrue( l_return.get( 1 ).raw() instanceof ZonedDateTime );

        Assertions.assertEquals( l_return.get( 1 ).<ZonedDateTime>raw().getYear(), l_return.get( 0 ).<ZonedDateTime>raw().getYear() );
        Assertions.assertEquals( l_return.get( 1 ).<ZonedDateTime>raw().getMonthValue(), l_return.get( 0 ).<ZonedDateTime>raw().getMonthValue() );
        Assertions.assertEquals( l_return.get( 1 ).<ZonedDateTime>raw().getDayOfMonth(), l_return.get( 0 ).<ZonedDateTime>raw().getDayOfMonth() );

        Assertions.assertEquals( l_return.get( 1 ).<ZonedDateTime>raw().getHour(), l_return.get( 0 ).<ZonedDateTime>raw().getHour() );
        Assertions.assertEquals( l_return.get( 1 ).<ZonedDateTime>raw().getMinute(), l_return.get( 0 ).<ZonedDateTime>raw().getMinute() );
        Assertions.assertEquals( l_return.get( 1 ).<ZonedDateTime>raw().getSecond(), l_return.get( 0 ).<ZonedDateTime>raw().getSecond() );
        Assertions.assertEquals( l_return.get( 1 ).<ZonedDateTime>raw().getNano(), l_return.get( 0 ).<ZonedDateTime>raw().getNano() );

        Assertions.assertNotEquals( l_return.get( 1 ).<ZonedDateTime>raw().getZone(), l_return.get( 0 ).<ZonedDateTime>raw().getZone() );
    }


    /**
     * test time
     */
    @Test
    public void time()
    {
        final List<ITerm> l_return = new ArrayList<>();

        Assertions.assertTrue(
            execute(
                new CTime(),
                false,
                Stream.of( "2007-12-03T10:15:30+03:00[Europe/Moscow]" ).map( CRawTerm::of ).collect( Collectors.toList() ),
                l_return
            ),
            "action execute error"
        );

        Assertions.assertEquals( 4, l_return.size() );
        Assertions.assertEquals( 10, l_return.get( 0 ).<Number>raw() );
        Assertions.assertEquals( 15, l_return.get( 1 ).<Number>raw() );
        Assertions.assertEquals( 30, l_return.get( 2 ).<Number>raw() );
        Assertions.assertEquals( 0, l_return.get( 3 ).<Number>raw() );
    }


    /**
     * test zone-id
     */
    @Test
    public void zoneid()
    {
        final List<ITerm> l_return = new ArrayList<>();

        new CZoneid().execute(
            false, IContext.EMPTYPLAN,
            Stream.of( "2006-10-04T10:17:13-05:00[America/New_York]", "2006-10-04T10:17:13+00:00[Europe/London]" ).map( CRawTerm::of )
                  .collect( Collectors.toList() ),
            l_return
        );

        Assertions.assertEquals( 2, l_return.size() );
        Assertions.assertEquals( "America/New_York", l_return.get( 0 ).raw() );
        Assertions.assertEquals( "Europe/London", l_return.get( 1 ).raw() );
    }


    /**
     * test apply-days minus
     *
     * @param p_action action
     * @param p_time input time
     * @param p_argument argument
     * @param p_result result
     */
    @ParameterizedTest
    @MethodSource( "generateapplyminus" )
    public void applysminus( final IAction p_action, final String p_time, final Integer p_argument, final String p_result )
    {
        final List<ITerm> l_return = new ArrayList<>();

        p_action.execute(
            false, IContext.EMPTYPLAN,
            Stream.of( "minus", p_argument, ZonedDateTime.parse( p_time ) ).map( CRawTerm::of ).collect( Collectors.toList() ),
            l_return
        );

        Assertions.assertEquals( 1, l_return.size() );
        Assertions.assertEquals( ZonedDateTime.parse( p_result ), l_return.get( 0 ).raw() );
    }

    /**
     * test apply-days plus
     *
     * @param p_action action
     * @param p_time time
     * @param p_argument argument
     * @param p_result result
     */
    @ParameterizedTest
    @MethodSource( "generateapplyplus" )
    public void applyplus( final IAction p_action, final String p_time, final Integer p_argument, final String p_result )
    {
        final List<ITerm> l_return = new ArrayList<>();

        p_action.execute(
            false, IContext.EMPTYPLAN,
            Stream.of( "plus", p_argument, ZonedDateTime.parse( p_time ) ).map( CRawTerm::of ).collect( Collectors.toList() ),
            l_return
        );

        Assertions.assertEquals( 1, l_return.size() );
        Assertions.assertEquals( ZonedDateTime.parse( p_result ), l_return.get( 0 ).raw() );
    }

    /**
     * test between
     *
     * @param p_action action
     * @param p_terms input terms
     * @param p_value input values
     */
    @ParameterizedTest
    @MethodSource( "generatebetween" )
    public void between( final IAction p_action, final Stream<String> p_terms, final Stream<Number> p_value )
    {
        final List<ITerm> l_return = new ArrayList<>();

        p_action.execute(
            false, IContext.EMPTYPLAN,
            p_terms.map( ZonedDateTime::parse ).map( CRawTerm::of ).collect( Collectors.toList() ),
            l_return
        );

        Assertions.assertArrayEquals(
            p_value.mapToLong( Number::longValue ).toArray(),
            l_return.stream().map( ITerm::<Number>raw ).mapToLong( Number::longValue ).toArray()
        );
    }

    /**
     * test the date
     */
    @Test
    public void datestring()
    {
        final List<ITerm> l_return = new ArrayList<>();
        final ZonedDateTime l_current = ZonedDateTime.now();

        new CDate().execute(
            false, IContext.EMPTYPLAN,
            Stream.of( "now" ).map( CRawTerm::of ).collect( Collectors.toList() ),
            l_return
        );

        Assertions.assertEquals( 5, l_return.size() );
        Assertions.assertArrayEquals(
            Stream.of(
                l_current.getYear(),
                l_current.getMonthValue(),
                l_current.getDayOfMonth(),
                l_current.getDayOfWeek().toString(),
                l_current.getDayOfYear()
            ).toArray(),
            l_return.stream().map( ITerm::raw ).toArray()
        );
    }

    /**
     * test date error
     */
    @Test
    public void dateerror()
    {
        Assertions.assertThrows( CExecutionIllegealArgumentException.class,
                                 () -> new CDate().execute(
                                     false, IContext.EMPTYPLAN,
                                     Stream.of( "xxx" ).map( CRawTerm::of ).collect( Collectors.toList() ),
                                     Collections.emptyList()
                                 )

        );
    }

    /**
     * test date by zone id structure
     */
    @Test
    public void datezonedate()
    {
        final List<ITerm> l_return = new ArrayList<>();
        final ZonedDateTime l_current = ZonedDateTime.now();

        new CDate().execute(
            false, IContext.EMPTYPLAN,
            Stream.of( l_current ).map( CRawTerm::of ).collect( Collectors.toList() ),
            l_return
        );

        Assertions.assertEquals( 5, l_return.size() );
        Assertions.assertArrayEquals(
            Stream.of(
                l_current.getYear(),
                l_current.getMonthValue(),
                l_current.getDayOfMonth(),
                l_current.getDayOfWeek().toString(),
                l_current.getDayOfYear()
            ).toArray(),
            l_return.stream().map( ITerm::raw ).toArray()
        );
    }
}
