// Copyright (c) 2014-2020 Dr. Colin Hirsch and Daniel Frey
// Please see LICENSE for license or visit https://github.com/taocpp/PEGTL/

#if !defined( __cpp_exceptions )
int main() {}
#else

#include <iostream>

#include <tao/pegtl.hpp>
#include <tao/pegtl/contrib/tracer.hpp>
#include <tao/pegtl/contrib/uri.hpp>

namespace pegtl = tao::TAO_PEGTL_NAMESPACE;

using grammar = pegtl::must< pegtl::uri::URI >;

int main( int argc, char** argv )  // NOLINT
{
   for( int i = 1; i < argc; ++i ) {
      std::cout << "Parsing " << argv[ i ] << std::endl;
      pegtl::argv_input<> in( argv, i );
      pegtl::parse< grammar, pegtl::nothing, pegtl::tracer >( in );
   }
   return 0;
}

#endif
